from get_sample.example.utils import myRandom


class RPS:

    def __init__(self) -> None:
        print(f'utils.py myRandom() 를 이용하여 가위바위보 객체를 생성합니다')

    def play(self):
        '''가위바위보 게임을 시작합니다.'''
        c = myRandom(1,4)
        p= input('가위', '바위', '보')
        rps = ['가위', '바위', '보']
        if p == rps[c-1]:
            print(f'컴퓨터: {rps[c-1]} 당신: {p}, 비겼습니다.')
        elif p == rps[c%3]:
            print(f'컴퓨터: {rps[c-1]} 당신: {p}, 졌습니다.')
        else:
            print(f'컴퓨터 : {rps[c-1]} 당신: {p}, 이겼습니다.')