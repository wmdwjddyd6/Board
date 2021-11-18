import java.util.*;

class Solution {
    public class Menu {
        String ings;
        int price;

        public Menu(String ings, int price) {
            this.ings = ings;
            this.price = price;
        }

        public String getIngs() {
            return ings;
        }

        public void setIngs(String ings) {
            this.ings = ings;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    public int solution(String[] ings, String[] menu, String[] sell) {
        int answer = 0;
        Map<String, Integer> ingsMap = new HashMap<String, Integer>();
        Map<String, Menu> menus = new HashMap<>();
        List<String> menuList = new ArrayList<>();
        String[] sTemp = new String[2];

        for(int i = 0; i < ings.length; i ++) { // 재료 넣기
            sTemp = ings[i].split(" ");
            ingsMap.put(sTemp[0], Integer.parseInt(sTemp[1]));
        }

        // System.out.println(ingsMap);

        sTemp = new String[3];

        for(int i = 0; i < menu.length; i ++) {
            sTemp = menu[i].split(" ");

            Menu m = new Menu(sTemp[1], Integer.parseInt(sTemp[2]));

            menus.put(sTemp[0], m);
        }

        Iterator<String> keys = menus.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            System.out.println("[Key]:" + key + " [Value]:" +  menus.get(key).getIngs());
        }
        System.out.println(menus);





        return answer;
    }
}