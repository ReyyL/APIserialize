import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

class Main {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException, JSONException {
        ArrayList<Member> people = new ArrayList<>();
        Object firstName, lastName, sex, university, bdate;
        String cityName;
        String api = "https://api.vk.com/method/groups.getMembers?" +
                "group_id=iritrtf_urfu&fields=bdate,city,sex,education&access_token=" +
                "f5493d1ef5493d1ef5493d1e03f53ddc23ff549f5493d1eaae58a467485251565e27d2a&v=5.126";
        URL url = new URL(api);
        URLConnection connection = url.openConnection();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = readAll(buffer);
        buffer.close();

        JSONObject members = new JSONObject(line);
        JSONArray arrayOfMembers = members.getJSONObject("response").getJSONArray("items");

        for (int i=0; i < arrayOfMembers.length(); i++) {
            JSONObject info = arrayOfMembers.getJSONObject(i);
            firstName = info.get("first_name");
            lastName = info.get("last_name");
            sex = info.getInt("sex") == 1 ? "Woman": "Man";

            try {
                bdate = info.getString("bdate");
            } catch (Exception e) {
                bdate = "hidden";
            }

            try {
                JSONObject city = info.getJSONObject("city");
                cityName = city.getString("title");
            } catch (Exception e) {
                cityName = "hidden";
            }

            try {
                university = info.getString("university_name");
            } catch (Exception e) {
                university = "hidden";
            }

            if (!firstName.equals("DELETED"))
                people.add(new Member(firstName, lastName, bdate, sex, cityName, university));
        }

        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("groupmember.dat")))
        {
            for (Member groupMember: people) {
                stream.writeObject(groupMember);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}