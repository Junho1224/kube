from sklearn.ensemble import RandomForestClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.naive_bayes import GaussianNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.svm import SVC
from sklearn.tree import DecisionTreeClassifier
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

    def preprocess(self):
        print(f'전처리')
        self.model.preprocess('train.csv', 'test.csv')
        return self.model

    def modeling(self, model_name:str):
        return {"DT": DecisionTreeClassifier(random_state=11),
                 "RF": RandomForestClassifier(random_state=11),
                   "NB": GaussianNB(),
                     "KNN": KNeighborsClassifier(),
                     "LR": LogisticRegression(solver='liblinear'),
                       "SVM": SVC()}.get(model_name, DecisionTreeClassifier())

    def learning(self, model_type, model_name:str):
        print(f'{model_name} Algorithm accuracy is ')
        return self.model.learning(model_type)

    def postprocess(self):
        print(f'후처리')
        this = self.model
    
    def submit(self):
        print(f'제출')
        this = self.model
        return this
   







