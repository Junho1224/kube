import os
import sys

import folium
from icecream import ic

import numpy as np
import requests
from sklearn import preprocessing
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from example.crime_util import Reader

from example.crime_model import CrimeModel
import pandas as pd
from dotenv import load_dotenv
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
print('*' *100)


# print(BASE_DIR)
load_dotenv(os.path.join(BASE_DIR, ".env"))



'''
문제정의 !
서울시의 범죄현황과 CCTV현황을 분석해서
정해진 예산안에서 구별로 다음해에 배분하는 기준을 마련하시오.
예산금액을 입력하면, 구당 할당되는 CCTV 카운터를 자동으로
알려주는 AI 프로그램을 작성하시오.
'''
class CrimeService:
    def __init__(self):
        self.data = CrimeModel()
        
        self.data.dname = 'C:/Users/bitcamp/vonteam-kuber/kube/chat-server/get_sample/get_sample/example/data/'
        self.data.sname = 'C:/Users/bitcamp/vonteam-kuber/kube/chat-server/get_sample/get_sample/example/save/'
        self.data.crime = 'crime_in_seoul.csv'
        self.data.cctv = 'cctv_in_seoul.csv'
        self.data.pop = 'pop_in_seoul.xls'
        self.crime_rate_columns = ['살인검거율', '강도검거율', '강간검거율', '절도검거율', '폭력검거율']
       
        self.crime_columns = ['살인 발생', '강도 발생', '강간 발생', '절도 발생', '폭력 발생']
        self.arrest_columns = ['살인 검거', '강도 검거', '강간 검거', '절도 검거', '폭력 검거']
       

    def crime_dataframe(self) -> pd.DataFrame:
        # index_col=0 해야 기존 index 값이 유지된다
        # 0 은 컬럼명 중에서 첫번째를 의미한다(배열구조)
        # pd.read_csv(f'경로/파일명/csv', index_col=0 = '인덱스로 지정할 column 명') Index 지정
     
        return pd.read_csv(f'{self.data.dname}{self.data.crime}', encoding='UTF-8', thousands=',')
    
    def cctv_dataframe(self) -> pd.DataFrame:
        # pd.read_csv('경로/파일명.csv') Index 를 지정하지 않음
        return pd.read_csv(f'{self.data.dname}{self.data.cctv}', encoding='UTF-8', thousands=',')
    
    def pop_dataframe(self) -> pd.DataFrame:
        return pd.read_excel(f'{self.data.dname}{self.data.pop}', header=1, usecols='B, D, G, J, N')
    

    

    def save_model(self, fname, dframe: pd.DataFrame) -> pd.DataFrame:
        '''
        풀옵션은 다음과 같다
        df.to_csv(f'{self.ds.sname}{fname}',sep=',',na_rep='NaN',
                         float_format='%.2f',  # 2 decimal places
                         columns=['ID', 'X2'],  # columns to write
                         index=False)  # do not write index
        '''
        return dframe.to_csv(f'{self.ds.sname}{fname}', sep=',', na_rep='NaN')
    

    def save_police_position(self) -> None:
        station_names = []
        crime = self.crime_dataframe()
        for name in crime['구별']:
            station_names.append('서울' + str(name[:-1]) + '경찰서')
        station_addreess = []
        station_lats = []
        station_lngs = []
        reader = Reader()
        gmaps = reader.gmaps(os.environ["api_key"])
        for name in station_names:
            t = gmaps.geocode(name, language='ko')
            print(t)
            station_addreess.append(t[0].get("formatted_address"))
            t_loc = t[0].get("geometry")
            station_lats.append(t_loc['location']['lat'])
            station_lngs.append(t_loc['location']['lng'])
        
        gu_names = []

        for name in station_addreess:
            tmp = name.split()
            gu_name = [gu for gu in tmp if gu[-1] == '구'][0]
            gu_names.append(gu_name)
        
        crime['구별'] = gu_names
        # 구 와 경찰서의 위치가 다른 경우 수작업
        crime.loc[crime['구별'] == '혜화서', ['구별']] = '종로구'
        crime.loc[crime['구별'] == '서부서', ['구별']] = '은평구'
        crime.loc[crime['구별'] == '강서서', ['구별']] = '양천구'
        crime.loc[crime['구별'] == '종암서', ['구별']] = '성북구'
        crime.loc[crime['구별'] == '방배서', ['구별']] = '서초구'
        crime.loc[crime['구별'] == '수서서', ['구별']] = '강남구'
        crime.to_csv(f'{self.data.sname}police_position.csv')

    def save_cctv_population(self) -> None:
        reader = Reader()
        population = reader.xls(f'{self.data.dname}pop_in_seoul', 2, 'B, D, G, J, N')
        

        cctv = self.cctv_dataframe()
        cctv.rename(columns={cctv.columns[0]: '구별'}, inplace=True)
        population.rename(columns={population.columns[0]: '구별',
                                   population.columns[1]: '인구수',
                                   population.columns[2]: '한국인',
                                   population.columns[3]: '외국인',
                                   population.columns[4]: '고령자'}, inplace=True)
        
        #population에서 NaN값이 있는지 확인 후 제거
        # population.dropna(axis=0, inplace=True)
        population.drop([26], inplace=True)

        population['외국인비율'] = population['외국인'] / population['인구수'] * 100
        population['고령자비율'] = population['고령자'] / population['인구수'] * 100

        cctv.drop(['2013년도 이전', '2014년', '2015년', '2016년'], axis=1, inplace=True)
        cctv_pop = pd.merge(cctv, population, on='구별')

        cor1 = np.corrcoef(cctv_pop['고령자비율'], cctv_pop['소계'])
        cor2 = np.corrcoef(cctv_pop['외국인비율'], cctv_pop['소계'])
        
        ic(cctv_pop)

        print(f'고령자비율과 CCTV의 상관계수 {str(cor1)} \n'
              f'외국인비율과 CCTV의 상관계수 {str(cor2)} ')
        """
         고령자비율과 CCTV 의 상관계수 [[ 1.         -0.28078554]
                                     [-0.28078554  1.        ]] 
         외국인비율과 CCTV 의 상관계수 [[ 1.         -0.13607433]
                                     [-0.13607433  1.        ]]
        r이 -1.0과 -0.7 사이이면, 강한 음적 선형관계,
        r이 -0.7과 -0.3 사이이면, 뚜렷한 음적 선형관계,
        r이 -0.3과 -0.1 사이이면, 약한 음적 선형관계,
        r이 -0.1과 +0.1 사이이면, 거의 무시될 수 있는 선형관계,
        r이 +0.1과 +0.3 사이이면, 약한 양적 선형관계,
        r이 +0.3과 +0.7 사이이면, 뚜렷한 양적 선형관계,
        r이 +0.7과 +1.0 사이이면, 강한 양적 선형관계
        고령자비율 과 CCTV 상관계수 [[ 1.         -0.28078554] 약한 음적 선형관계
                                    [-0.28078554  1.        ]]
        외국인비율 과 CCTV 상관계수 [[ 1.         -0.13607433] 거의 무시될 수 있는
                                    [-0.13607433  1.        ]]                        
         """
        cctv_pop.to_csv(f'{self.data.sname}cctv_pop.csv')

    def save_crime_arresest_nomalization(self) -> None:
        crime = self.crime_dataframe()
        cctv = self.cctv_dataframe()
        # 발생, 검거를 따로 묶고 CCTV와 상관계수 구하기.

        ic(crime)

        crime['총 범죄'] = crime.loc[:, self.crime_columns].sum(axis=1)
        crime['총 검거'] = crime.loc[:, self.arrest_columns].sum(axis=1)
        crime.rename(columns={'관서명': '구별'}, inplace=True)

        pivot_crime = crime.pivot_table(index='구별', aggfunc=np.sum)
        ic(pivot_crime)

        
        pivot_crime['살인검거율'] = pivot_crime['살인 검거'] / pivot_crime['살인 발생'] * 100
        pivot_crime['강도검거율'] = pivot_crime['강도 검거'] / pivot_crime['강도 발생'] * 100
        pivot_crime['강간검거율'] = pivot_crime['강간 검거'] / pivot_crime['강간 발생'] * 100
        pivot_crime['절도검거율'] = pivot_crime['절도 검거'] / pivot_crime['절도 발생'] * 100
        pivot_crime['폭력검거율'] = pivot_crime['폭력 검거'] / pivot_crime['폭력 발생'] * 100
        pivot_crime.drop(['강간 검거', '강도 검거', '살인 검거', '절도 검거', '폭력 검거'], axis=1, inplace=True)

        for i in self.crime_rate_columns:
            pivot_crime.loc[pivot_crime[i] > 100, i] = 100

        x = pivot_crime[self.crime_rate_columns].values
        min_max_scaler = preprocessing.MinMaxScaler()
        """     
        피쳐 스케일링(Feature scalining)은 해당 피쳐들의 값을 일정한 수준으로 맞춰주는 것이다.
        이때 적용되는 스케일링 방법이 표준화(standardization) 와 정규화(normalization)다.
        
        1단계: 표준화(공통 척도)를 진행한다.
            표준화는 정규분포를 데이터의 평균을 0, 분산이 1인 표준정규분포로 만드는 것이다.
            x = (x - mu) / sigma
            scale = (x - np.mean(x, axis=0)) / np.std(x, axis=0)
        2단계: 이상치 발견 및 제거
        3단계: 정규화(공통 간격)를 진행한다.
            정규화에는 평균 정규화, 최소-최대 정규화, 분위수 정규화가 있다.
             * 최소최대 정규화는 모든 데이터를 최대값을 1, 최솟값을 0으로 만드는 것이다.
            도메인은 데이터의 범위이다.
            스케일은 데이터의 분포이다.
            목적은 도메인을 일치시키거나 스케일을 유사하게 만든다.     
        """
        # x_scaled = min_max_scaler.fit_transform(x.astype(float))
        # police_norm = pd.DataFrame(x_scaled, columns=self.crime_rate_columns, index=pivot_crime.index)
        # police_norm[self.crime_columns] = pivot_crime[self.crime_columns]

        # ic(police_norm)

        # police_norm['범죄'] = np.sum(police_norm[self.crime_columns], axis=1)
        # police_norm['검거'] = np.sum(police_norm[self.arrest_columns], axis=1)
        # police_norm.to_csv(f'{self.data.dname}police_norm.csv', sep=',', encoding='utf-8')



        ic(crime.columns)

        crime['구별'] = crime['구별'].str[:-1] + '구'
        crime = crime.sort_values(by='구별').reset_index(drop=True)

        # ic(crime)

        cctv.rename(columns={cctv.columns[0]: '구별'}, inplace=True)
        cctv.drop(['2013년도 이전', '2014년', '2015년', '2016년'], axis=1, inplace=True)
        # ic(cctv)

        cctv_crime = pd.merge(cctv,crime, on='구별')
        ic(cctv_crime)

        cctv_crime.to_csv(f'{self.data.dname}police_norm.csv', sep=',', encoding='utf-8')

        cor1 = np.corrcoef(cctv_crime['총 범죄'], cctv_crime['소계'])
        cor2 = np.corrcoef(cctv_crime['총 검거'], cctv_crime['소계'])

        print(f'총 범죄와 CCTV의 상관계수 {str(cor1)} \n'
              f'총 검거와 CCTV의 상관계수 {str(cor2)} ')
        
    def folium_test(self):
        reader = Reader()
    
        state_geo = reader.json(f'{self.data.dname}us-states')

        state_data = reader.csv(f'{self.data.dname}us_unemployment')

        m = folium.Map(location=[48, -102], zoom_start=3)

        folium.Choropleth(
            geo_data=state_geo,
            name="choropleth",
            data=state_data,
            columns=["State", "Unemployment Rate"],
            key_on="feature.id",
            fill_color="YlGn",
            fill_opacity=0.7,
            line_opacity=0.2,
            legend_name="Unemployment Rate (%)",
        ).add_to(m)

        folium.LayerControl().add_to(m)

        m.save(f'{self.data.sname}us_states.html')


    def draw_crime_map(self):
        reader = Reader()
        state_geo = reader.json(f'{self.data.dname}kr-states')
        state_data = reader.csv(f'{self.data.sname}police_norm')
        m = folium.Map(location=[37.5502, 126.982], zoom_start=12, title='Stamen Toner')


        station_names = []
        for name in state_data['구별']:
            station_names.append('서울' + str(name[:-1]) + '경찰서')


        folium.Choropleth(
            geo_data=state_geo,
            name="choropleth",
            data=state_data,
            columns=["구별", "총 범죄"],
            key_on="feature.id",
            fill_color="PuRd",
            fill_opacity=0.7,
            line_opacity=0.2,
            legend_name="Crime Rate (%)",
        ).add_to(m)

        folium.LayerControl().add_to(m)

        m.save(f'{self.data.sname}kr_states.html')

    def draw_crime_map1(self):
        reader = Reader()
        state_geo = reader.json(f'{self.data.dname}kr-states')
        state_data = reader.csv(f'{self.data.sname}police_norm')
        m = folium.Map(location=[37.5502, 126.982], zoom_start=12, title="Stamen Toner")
        police_position = reader.csv(f'{self.data.sname}police_position')
        police_norm = reader.csv(f'{self.data.sname}police_norm')
        crime = self.crime_dataframe()
        station_names = []
        for name in crime['관서명']:
            sample = '서울' + str(name[:-1]) + '경찰서'
            print(f'---> {sample}')
            station_names.append(sample)
        station_addreess = []
        station_lats = []
        station_lngs = []
        # gmaps = reader.gmaps(os.environ["api_key"])
        # for i, name in enumerate(station_names):
    
        #     # if name == '서울강서경찰서':
        #     #     print('서울강서경찰서 위치가 정확하지 않아 수동으로 입력합니다.')
        #     #     temp = gmaps.geocode(name, language='ko')
        #     # else:
        #     #     print('서울종암경찰서 위치가 정확하지 않아 수동으로 입력합니다.')
        #     #     temp = self.jongam_police_info()
        #     station_addreess.append(temp[0].get("formatted_address"))
        #     t_loc = temp[0].get("geometry")
        #     station_lats.append(t_loc['location']['lat'])
        #     station_lngs.append(t_loc['location']['lng'])
        # police_position['lat'] = station_lats
        # police_position['lng'] = station_lngs

        temp = police_position[self.arrest_columns] / police_position[self.arrest_columns].max()
        police_position['검거' ] = np.sum(temp, axis=1)

        folium.Choropleth(
            geo_data=state_geo,
            name="choropleth",
            data=tuple(zip(police_norm['구별'], police_norm['범죄'])),
            columns=["State", "Crime Rate"],
            key_on="feature.id",
            fill_color="PuRd",
            fill_opacity=0.7,
            line_opacity=0.2,
            legend_name="Crime Rate (%)",
        ).add_to(m)

        #CircleMarker
        for i in police_position.index:
            folium.CircleMarker([police_position['lat'][i], police_position['lng'][i]],
                                radius=police_position['검거'][i] * 10,
                                color='#3186cc', fill_color='#3186cc').add_to(m)
        folium.LayerControl().add_to(m)
        m.save(f'{self.data.sname}kr_states.html')
       





    
if __name__ == "__main__":
    service = CrimeService()

    # crime_df = service.crime_dataframe()
    # cctv_df = service.cctv_dataframe()
    # # ic(crime_df)
    # # ic(cctv_df)
    # # pop_df = service.pop_dataframe()
    # # pop_df = pop_df.drop(index=0).reset_index(drop=True)
    # # pop_df = service.save_cctv_population()

    # service.save_crime_arresest_nomalization()
    # folium_test = service.draw_crime_map()
    service.draw_crime_map1()
    

    