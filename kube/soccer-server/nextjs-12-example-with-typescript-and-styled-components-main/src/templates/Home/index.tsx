import { Heading } from 'components/Heading';

function Home() {
  return (<>


    <Heading>Soccer</Heading>;

    <table>
      <thead>
        <tr>
          <th>No.</th>
          <th>문제</th>
          <th>답</th>
        </tr>
      </thead>
    </table>



    <tbody>
      <tr>
        <th>1</th>
        <th>{'전체 축구팀 목록을 팀이름 오름차순으로 출력'}</th>
        <th>답</th>
      </tr>
      <tr>
        <th>2</th>
        <th>{'플레이어의 포지션 종류를 나열'}</th>
        <th>답</th>
      </tr>
      <tr>
        <th>3</th>
        <th>{'플레이어의 포지션 종류를 나열'}</th>
        <th>답</th>
      </tr>
    </tbody>




  </>)
}

export default Home;
