from dataclasses import dataclass

import pandas as pd
import numpy as np
from icecream import ic
from sklearn.ensemble import RandomForestClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import KFold, cross_val_score
from sklearn.tree import DecisionTreeClassifier
from app.api.context.models import Models
import matplotlib.pyplot as plt
import seaborn as sns
from app.api.context.data_sets import DataSets


class TitanicModel(object):

    model = Models()
    dataset = DataSets()

    def preprocess(self, train_fname, test_fname) -> object:
        this = self.dataset
        that = self.model

        feature = ['PassengerId','Survived','Pclass','Name','Sex','Age','SibSp','Parch','Ticket','Fare','Cabin','Embarked']

        this.train = that.new_dataframe_no_index(train_fname)
        this.test = that.new_dataframe_no_index(test_fname)

        
        # null = this.test.isnull().sum()
        # print(null)
        
        this.id = this.test['PassengerId']
        this.label = this.train['Survived']

        this.train.drop('Survived', axis=1, inplace=True)
        this = self.drop_feature(this, 'SibSp', 'Parch', 'Cabin', 'Ticket')
        
        
        this = self.sex_nominal(this)
        this = self.drop_feature(this, 'Sex')

        this = self.extract_title_from_name(this)
        title_mapping = self.remove_duplicate_title(this)
        this = self.title_nominal(this, title_mapping)
        this = self.drop_feature(this, 'Name')
        this = self.embarked_nominal(this)
        # this = self.drop_feature(this, 'Embarked')


        this = self.age_ratio(this)
        this = self.drop_feature(this, 'Age')
        # this = self.drop_feature(this, 'AgeGroup')
        

        this = self.pclass_ordianl(this)
        this = self.fare_ratio(this)
        this = self.drop_feature(this, 'Fare')

        self.df_info(this)

        
        this.train['Title'] = this.train['Title'].fillna(0)
        this.test['Title'] = this.test['Title'].fillna(0)

        # isnulln = this.train.isnull().sum()
        # isnullt = this.train.isnull().sum()
        # print(isnulln)
        # print(isnullt)

        # acc = self.learning(train_fname, test_fname)
        # acc = self.get_accuracy(this, self.create_k_fold())
        # print(acc)
        return this

    
    @staticmethod
    def name_nominal(this) -> object:
        this.train['Name'] = this.train['Name'].str.extract('([A-Za-z]+)\.')
        this.test['Name'] = this.test['Name'].str.extract('([A-Za-z]+)\.')
        return this


    @staticmethod
    def extract_title_from_name(this) -> pd.DataFrame:
        for i in [this.train, this.test]:
            i['Title'] = i['Name'].str.extract('([A-Za-z]+)\.', expand=False) # .이 있는값을 뽑겠다는 뜻
        return this
    
    @staticmethod
    def remove_duplicate_title(this) -> pd.DataFrame:
        a = []
        for these in [this.train, this.test]:
            a += list(set(these['Title']))
        a = list(set(a))
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
        title_mapping = {'Mr':1, 'Ms':2, 'Mrs':3, 'Master':4, 'Roayal':5, 'Rare':6}
        return title_mapping


    @staticmethod
    def title_nominal(this, title_mapping) -> pd.DataFrame:
        for these in [this.train, this.test]:
            these['Title'] = these['Title'].replace(['Countess', 'Lady', 'Sir'], 'Royal')
            these['Title'] = these['Title'].replace(['Capt','Col','Don','Dr','Major','Rev','Jonkheer','Dona','Mme'], 'Rare')
            these['Title'] = these['Title'].replace(['Mlle'], 'Mr')
            these['Title'] = these['Title'].replace(['Miss'], 'Ms')
            # Master 는 변화없음
            # Mrs 는 변화없음
            these['Title'] = these['Title'].fillna(0,inplace=False)
            these['Title'] = these['Title'].map(title_mapping)
        return this
    
    @staticmethod
    def age_ratio(this) -> pd.DataFrame:
        train = this.train
        test = this.test
        age_mapping = {'Unknown':0 , 'Baby': 1, 'Child': 2, 'Teenager' : 3, 'Student': 4,
                       'Young Adult': 5, 'Adult':6,  'Senior': 7}
        train['Age'] = train['Age'].fillna(-0.5)
        test['Age'] = test['Age'].fillna(-0.5) # 왜 NaN 값에 -0.5 를 할당할까요 ?
        bins = [-1, 0, 5, 12, 18, 24, 35, 60, np.inf] # 이것을 이해해보세요
        labels = ['Unknown', 'Baby', 'Child', 'Teenager', 'Student', 'Young Adult', 'Adult', 'Senior']
        
        for these in train, test:
            these['Age'] = pd.cut(these['Age'], bins, labels=labels)
            these['AgeGroup'] = these['Age'].map(age_mapping) # map() 사용

        return this
    
    @staticmethod
    def embarked_nominal(this) -> pd.DataFrame:
        this.train['Embarked'] = this.train['Embarked'].fillna('S')
        this.test['Embarked'] = this.test['Embarked'].fillna('S')
        this.train['Embarked'] = this.train['Embarked'].map({'S':1, 'C':2, 'Q':3})
        this.test['Embarked'] = this.test['Embarked'].map({'S':1, 'C':2, 'Q':3})
        return this
    

    @staticmethod
    def sex_nominal(this) -> pd.DataFrame:
        # print(f"SEX : {this.train['Sex'].value_counts()}")
        train = this.train
        test = this.test
        sex_mapping = {'male':0 , 'female': 1}
        for these in train, test:
            these['Gender'] = these['Sex'].map(sex_mapping)

        return this
    
    @staticmethod
    def pclass_ordianl(this) -> pd.DataFrame:
        this.train
        return this
    
    @staticmethod
    def fare_ratio(this) -> pd.DataFrame:
        bins = [-1, 0, 8, 15, 31, np.inf]
        lables = ['Unknown', '1_quartile', '2_quartile', '3_quartile', '4_quartile']
        fare_mapping = {'Unknown':0, '1_quartile':1, '2_quartile':2, '3_quartile':3, '4_quartile':4}
        for these in [this.train, this.test]:
            these['FareBand'] = pd.cut(these['Fare'], bins, labels=lables)
            these['FareBand'] = these['FareBand'].map(fare_mapping)
        return this
    
    
    def df_info(self, this):
        print('='*50)
        print(f'3. Train 의 상위 데이터는 \n{this.train.head()} 이다.')
        print(f'7. Test 의 상위 데이터는 \n{this.test.head()} 이다.')
        print('='*50)
    
    @staticmethod
    def drop_feature(this, *feature) -> pd.DataFrame:

        # 컴프리핸션
        [i.drop(j, axis=1, inplace=True) for i in [this.train, this.test] for j in feature]

        return this
    
    @staticmethod
    def this_info(this):
        [print(f'{i.info()}') for i in [this.train, this.test]]

    @staticmethod
    def this_head(this):
        [print(f'{i.head(3)}') for i in [this.train, this.test]]

    
    @staticmethod
    def pclss_ordinal(this) -> pd.DataFrame:
        this.train['Pclass'] = this.train['Pclass'].map({1: 1, 2: 2, 3: 3}) 
        this.test['Pclass'] = this.test['Pclass'].map({1: 1, 2: 2, 3: 3}) 
        return this
    


    def modeling(self, model_name) -> object:

        return 
    

    @staticmethod
    def create_k_fold() -> object:
        print(f'K폴드')
        return KFold(n_splits=10, shuffle=True, random_state=0)
    

    def learning(self, model_type) -> Models:
        k_fold = self.create_k_fold()
        print(k_fold)
        accuarcy = self.get_accuracy(self.dataset, model_type, k_fold)
        return accuarcy
    

    def get_accuracy(self, this, model_type, k_fold) -> float:
        score = cross_val_score(model_type, this.train, this.label, cv=k_fold, n_jobs=1, scoring='accuracy')
        return round(np.mean(score) * 100, 2)
    

    

    

    

    
    
    

