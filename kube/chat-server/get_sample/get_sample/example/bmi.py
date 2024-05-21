from get_sample.example.utils import Member


class BMI():
    def __init__(self) -> None:
        '''utils.py / Members(), myRandom() 를 이용하여 BMI 지수를 구하는 계산기를 작성합니다.'''
        
    def getBMI(self, height, weight):
        '''키와 몸무게를 입력받아 BMI 지수를 계산합니다.'''

        this = Member()
        
        return round(weight / (height/100)**2)