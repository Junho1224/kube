import random


class BitBank:

    accounts = []


    def __main__(self,name,account_number,money) -> None:
        '''
        [요구사항(RFP)]
        은행이름은 비트은행이다.
        입금자 이름(name), 계좌번호(account_number), 금액(money) 속성값으로 계좌를 생성한다.
        계좌번호는 3자리-2자리-6자리 형태로 랜덤하게 생성된다.
        예를들면 123-12-123456 이다.
        금액은 100 ~ 999 사이로 랜덤하게 입금된다. (단위는 만단위로 암묵적으로 판단한다)
        '''

        self.bank_name = '비트은행'
        self.name = name
        self.account_number = account_number
        self.money = money

        while 1:
            menu = int(input('0.종료 1.계좌생성 2.계좌목록 3.입금 4.출금 5.계좌해지 6.계좌조회'))
            if menu == 0:
                break
            elif menu == 1:
                self.create_account()
            elif menu == 2:
                self.list_account()
            elif menu == 3:
                self.deposit()
            elif menu == 4:
                self.withdraw()
             

            

    def create_account_number(self):
        return f'{random.randint(100,999)}-{random.randint(10,99)}-{random.randint(100000,999999)}'

    def create_account(self):
        name = input('이름')
        account_number = self.create_account_number()
        money = random.randint(100,999)*10000
        new_account = BitBank(name=name,account_number=account_number,money=money)
        BitBank.accounts.append(new_account)
    
    def list_account(self):
        # for account in BitBank.accounts:
        #     print(account)
        pass

    def deposit(self):
        depoaccount = input('입금할 계좌번호를 입력하세요(-제외하면못찾는데오또카지)')
        howmany = input("얼마를 입금할까요?")
        for account in BitBank.accounts:
            if account.account_number == depoaccount:
                account.money += int(howmany)
                print(f'{account.name}님의 계좌번호 {account.account_number}에 {howmany}원 입금되었습니다.')
                break
        else:
            print('해당하는 계좌번호가 없습니다.')




    def withdraw(self):
        withaccount = input('출금할 계좌번호를 입력하세요(-제외하면못찾는데오또카지)')
        howmany = input("얼마를 출금할까요?")
        for account in BitBank.accounts:
            if account.account_number == withaccount:
                account.money -= int(howmany)
                print(f'{account.name}님의 계좌번호 {account.account_number}에 {howmany}원 출금되었습니다.')
                break
        else:
            print('해당하는 계좌번호가 없습니다.')





    

        
      
if __name__ == "__main__":
    BitBank.__main__

    


    
