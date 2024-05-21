from urllib.request import urlopen

from bs4 import BeautifulSoup
import pandas as pd 

class ScrapBugs:


    def __init__(self) -> None:
        pass


    def scrap(self):
        print('벅스뮤직에서 데이터를 수집합니다.')
        url = 'https://music.bugs.co.kr/chart/track/realtime/total?'
        html_doc = urlopen(url)
        soup = BeautifulSoup(html_doc, 'lxml')
        list1 = self.find_music(soup, 'artist')
        list2 = self.find_music(soup, 'title')
        list1[0].split("\n")[1]

        list1, list2 = self.delete_n(list1, list2)


        a = [i if i==0 or i==0 else i for i in range(1)]
        b = [i if i==0 or i==0 else i for i in list1]
        c = [(i,j) for i,j in enumerate([])]
        d = {i:j for i,j in zip(list1, list2)}
        l = [i+j for i,j in zip(list1, list2)]
        l2 = list(zip(list1, list2))
        d1 = dict(zip(list1, list2))

        e = self.make_dataframe(list1, list2)
        s = self.save_csv(e)
        print(d)
        return d
    
    def find_music(self, soup, class_name):
        list = soup.find_all(name='p', attrs={'class': class_name})
        return [i.get_text() for i in list]
    
    def make_dataframe(self, list1, list2):
        return pd.DataFrame(list(zip(list1, list2)), columns=['가수', '제목'])
    
    def delete_n(self, list1, list2):
        return [i.split('\n')[1] for i in list1], [i.split('\n')[1] for i in list2]

    def save_csv(self, df):
        df.to_csv('bugs_music.csv', index=False, encoding='utf-8')
    


if __name__ == "__main__":
    scrapBugs = ScrapBugs()
    scrapBugs.scrap()

