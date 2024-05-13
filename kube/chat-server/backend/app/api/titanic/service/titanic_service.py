from app.api.titanic.model.titanic_model import TitanicModel
import pandas as pd

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
        self.df_info(this)


        this.id = this.test['PassengerId']

        this = self.drop_feature(this, 'Name','SibSp','Parch','Ticket','Cabin')

        print(f'트레인 컬럼 : {this.train.columns}')
        print(f'테스트 컬럼 : {this.test.columns}')



        this = self.create_train(this)
        

    def new_model(self, payload)->object:
        this = self.model
        this.context = 'C:/Users/bitcamp/vonteam-kuber/kube/chat-server/backend/app/api/titanic/data/'
        this.fname = payload
        return pd.read_csv(this.context + this.fname)
    
    @staticmethod
    def create_train(this) -> str:
        return this.train.drop('Survived',axis=1) #axis=0 행을 삭제 axis=1 열을 삭제
    
    @staticmethod
    def create_label(this) -> str:
        return this.train['Survived'] # Survived 칼럼의 값을 가져옴
    
    @staticmethod
    def drop_feature(this, *feature) -> object:
        
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
    def df_info(this):
        # for i in [this.train, this.test]:
        #     print(f'{i.info(3)}')
            

        [print(f'{i.info(3)}') for i in [this.train, this.test]]
           
            

