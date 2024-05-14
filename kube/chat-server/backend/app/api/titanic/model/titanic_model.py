from dataclasses import dataclass


from icecream import ic
from app.api.context.models import Models
from app.api.context.data_sets import DataSets


class TitanicModel(object):
    model = Models()
    dataset = DataSets()


    def preprocess(self, train_fname, test_fname):
        this = self.model
        this = self.dataset


        this.ds.train = self.new_model(train_fname)
        this.ds.test = self.new_model(test_fname)
    
        feature = ['PassengerId','Survived','Pclass','Name','Sex','Age','SibSp','Parch', 'Ticket','Fare','Cabin','Embarked']
        this.train = this.new_dframe(train_fname)
        this.test = this.new_dframe(test_fname)
        this.id = this.test['PassengerId']
        this.label = this.train['Survived']
        this.train = this.train.drop(['Survived'], axis=1)
        this = self.drop_feature(this,'SibSp','Parch','Ticket','Cabin')


        return this

