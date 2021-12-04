import java.util.*;

class Solution {
    public String solution(String character, String[] monsters) {
        String answer = "";
        String[] s = character.split(" ");
        String[] monster;
        int[] player = new int[s.length];
        int[] monsterInfo;
        int minutes = 0;
        int[] exp = new int[monsters.length];
        double[] exp2 = new double[monsters.length];
        List<Integer> equalsExp = new ArrayList<>();
        List<Double> diviExp = new ArrayList<>();
        int maxIndex = 0;
        double max = -1;
        int maxCount = 0;
        int equalsCount = 0;
        int singleMax = -1;


        for(int i = 0; i < s.length; i ++) { // 플레이어 정보 int 변환
            player[i] = Integer.parseInt(s[i]);
            // System.out.println("player : " + player[i]);
        }


        for(int m = 0; m < monsters.length; m ++) {
            monster = monsters[m].split(" ");
            monsterInfo = new int[monster.length - 1];

            for(int j = 0; j < monsterInfo.length; j ++) { // 몬스터 이름 제외 정보 int 업데이트
                monsterInfo[j] = Integer.parseInt(monster[j + 1]);
                // System.out.println("monsterInfo : " + monsterInfo[j]);
            }

            int power = player[1] - monsterInfo[3];
            int hited = monsterInfo[2] - player[2];

            if(power <= 0 || hited >= player[0]) {
                exp[m] = 0;
                continue;
            }

            while(monsterInfo[1] > 0) {
                monsterInfo[1] -= power;
                minutes += 1;
            }

            exp[m] = monsterInfo[0];
            exp2[m] = monsterInfo[0] / minutes;
            minutes = 0;
            System.out.println("exp : " + exp[m]);

            if(exp[m] > singleMax) {
                singleMax = m;
            }

            diviExp.add(exp2[m]);
        }

        System.out.println("singleMax : " + singleMax);

        for(int i = 0; i < diviExp.size(); i ++) {
            System.out.println("divi : " + diviExp.get(i));
            if(diviExp.get(i) > max) {
                max = diviExp.get(i);
                maxIndex = i;
            }
        }
        System.out.println("maxIndex : " + maxIndex);
//         for(int i = 0; i < diviExp.size(); i ++) {
//             if(diviExp.get(i) == max) {
//                 maxCount += 1;
//             }
//             if(maxCount == 2) {
//                 monster = monsters[singleMax].split(" ");
//                 answer = monster[0];

//                 return answer;
//             }
//         }

        monster = monsters[maxIndex].split(" ");
        answer = monster[0];

        return answer;
    }
}

// 0  1  2
// 체 공 방
// 이름, 경, 체, 공, 방
// 0    1    2   3  4
// 경, 체, 공, 방
// 0   1   2  3