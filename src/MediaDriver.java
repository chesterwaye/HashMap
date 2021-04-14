import java.util.Set;

/**
 * Class: MediaDriver
 *
 * @author Chester Waye
 * @version 1.0 * Course : ITEC 3150, Spring 2021 Written: 4/13/2021
 * Written: January 18, 2012
 * <p>
 * <p>
 * This class –now describe what the class does
 * <p>
 * Purpose: –Describe the purpose of this class
 */
public class MediaDriver {
    public static void main(String[] args) {

        MediaHashMap<String, Media> map = new MediaHashMap<>();

        map.put("1", new Media("001", "Star Wars: The Rise of SkyWalker", "Film"));
        map.put("2", new Media("002", "Star Wars: The Last Jedi", "Film"));
        map.put("3", new Media("003", "Star Wars: The Force Awakens", "Film"));

        Set<String> mapSet = map.keySet();
        for (String mapKeys : mapSet) {
            Media films = map.get(mapKeys);
            System.out.println(mapKeys + " | " + films);
        }

    }

}
