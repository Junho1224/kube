from app.api.titanic.model.titanic_model import TitanicModel
import pandas as pd
import numpy as np
import icecream as ic


# Survived - 생존유무, target 값. (0 = 사망, 1 = 생존)
# Name - 탑승객 성명
# Pclass - 티켓 클래스. (1 = 1st, 2 = 2nd, 3 = 3rd)
# Sex - 성별
# Age - 나이(세)
# SibSp - 함께 탑승한 형제자매, 배우자 수 총합
# Parch - 함께 탑승한 부모, 자녀 수 총합
# Embarked - 탑승 항구
# Fare - 탑승 요금
# Ticket - 티켓 넘버
# Cabin - 객실 넘버


class TitanicService:
    model = TitanicModel()

    def process(self):
        print(f'프로세스 시작')

        this = self.model #객체가 인스턴스로 만들어짐 

        this.train = self.new_model("train.csv")
        this.test = self.new_model("test.csv")


        print(f'피처 추출 전')
        self.this_head(this)
       

        this = self.drop_feature(this, 'Name','SibSp','Parch','Ticket','Cabin')

        this = self.name_nominal(this)

        print(f'피처 삭제 후')
        self.this_head(this)


        this = self.pclss_ordinal(this)
        this = self.sex_nominal(this)
        this = self.age_ordinal(this)
        this = self.fare_ordinal(this)
        this = self.embarked_nominal(this)


        print(f'정제 후')
        self.this_head(this)


        this = self.create_train(this)
        

    
    @staticmethod
    def create_train(this) -> str:
        return this.train.drop('Survived',axis=1) #axis=0 행을 삭제 axis=1 열을 삭제
    
    @staticmethod
    def create_label(this) -> str:
        return this.train['Survived'] # Survived 칼럼의 값을 가져옴
    
    @staticmethod
    def drop_feature(this, *feature) -> pd.DataFrame:
        
        # for i in feature:
        #     this.train = this.train.drop(i, axis=1, inplace=True)
        #     this.test = this.test.drop(i, axis=1, inplace=True)
        
        # for i in [this.train, this.test]:
        #     for j in feature:
        #         i.drop(j, axis=1, inplace=True)

        # 컴프리핸션
        [i.drop(j, axis=1, inplace=True) for i in [this.train, this.test] for j in feature]

        # this.train = this.train.drop(list(feature), axis=1,inplace=True)
        # this.test = this.test.drop(list(feature), axis=1,inplace=True)
        return this
    
    @staticmethod
    def this_info(this):
        [print(f'{i.info()}') for i in [this.train, this.test]]

    @staticmethod
    def this_head(this):
        [print(f'{i.head(3)}') for i in [this.train, this.test]]

    @staticmethod
    def this_isnull(this):
        [print(f'{i.isnull().sum()}') for i in [this.train, this.test]]



    @staticmethod
    def name_nominal(this) -> pd.DataFrame:
        return this
    
    @staticmethod
    def extract_title_from_name(this) -> pd.DataFrame:
        
        for i in [this.train, this.test]:
            i['Title'] = i['Name'].str.extract('([A-Za-z]+)\.',expand=False)
        return this
    
    @staticmethod
    def remove_duplicate_title(this)->pd.DataFrame:
        a=[]
        for these in [this.train, this.test]:
            a += list(set(these['Title']))
        a=list(set(a))
        print(a)
        '''
        ['Mr', 'Sir', 'Major', 'Don', 'Rev', 'Countess', 'Lady', 'Jonkheer', 'Dr',
        'Miss', 'Col', 'Ms', 'Dona', 'Mlle', 'Mme', 'Mrs', 'Master', 'Capt']
        Royal : ['Countess', 'Lady', 'Sir']
        Rare : ['Capt','Col','Don','Dr','Major','Rev','Jonkheer','Dona','Mme' ]
        Mr : ['Mlle']
        Ms : ['Miss']
        Master
        Mrs
        '''

        title_mapping = {'Mr':1, 'Ms':2, 'Mrs':3, 'Master':4, 'Royal':5, 'Rare':6}
        return title_mapping
    

    @staticmethod
    def title_nominal(this,title_mapping) -> pd.DataFrame:
        for these in [this.train, this.test]:
            these['Title'] = these['Title'].replace(['Countess', 'Lady', 'Sir'], 'Royal')
            these['Title'] = these['Title'].replace(['Capt','Col','Don','Dr','Major','Rev','Jonkheer','Dona','Mme'], 'Rare')
            these['Title'] = these['Title'].replace(['Mlle'], 'Mr')
            these['Title'] = these['Title'].replace(['Miss'], 'Ms')
            # Master 는 변화없음
            # Mrs 는 변화없음
            these['Title'] = these['Title'].fillna(0)
            these['Title'] = these['Title'].map(title_mapping)
        return this
    

    @staticmethod
    def age_ratio(this)->pd.DataFrame:
        train = this.train
        test = this.test
        age_mapping = {'Unknown':0 , 'Baby': 1, 'Child': 2, 'Teenager' : 3, 'Student': 4,
                       'Young Adult': 5, 'Adult':6,  'Senior': 7}
        train['Age'] = train['Age'].fillna(-0.5)
        test['Age'] = test['Age'].fillna(-0.5) # 왜 NaN 값에 -0.5 를 할당할까요 ?
        bins = [-1, 0, 5, 12, 18, 24, 35, 60, np.inf] # 이것을 이해해보세요
        labels = ['Unknown', 'Baby', 'Child', 'Teenager', 'Student', 'Young Adult', 'Adult', 'Senior']

        for these in train, test:
            these['AgeGroup'] = pd.cut(these['Age'], bins, labels=labels)
            these['AgeGroup'] = these['AgeGroup'].map(age_mapping)


    @staticmethod
    def pclss_ordinal(this) -> pd.DataFrame:
        this.train['Pclass'] = this.train['Pclass'].map({1: 1, 2: 2, 3: 3}) 
        this.test['Pclass'] = this.test['Pclass'].map({1: 1, 2: 2, 3: 3}) 
        return this
    
    @staticmethod
    def sex_nominal(this) -> pd.DataFrame:
        return this
           
    @staticmethod
    def age_ordinal(this) -> pd.DataFrame:
        return this
    
    @staticmethod
    def fare_ordinal(this) -> pd.DataFrame:
        return this
    
    @staticmethod
    def embarked_nominal(this) -> pd.DataFrame:
        return this









    @staticmethod
    def fillna(this)->pd.DataFrame:

        this['Age'].fillna(this['Age'].mean(), inplace=True)
        this['Cabin'].fillna('N', inplace=True)
        this['Embarked'].fillna('N', inplace=True)
        this['Fare'].fillna('0', inplace=True)
        return this




