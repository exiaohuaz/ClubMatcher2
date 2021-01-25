//this is a test java file

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.lang.Math;
import java.util.ArrayList;
import java.io.*;
import java.net.*;


/* setName(string name)
 * setTags(ArrayList<Tags> append)
 * setTimeCommitment(int minutes)
 * setClubSize(int size)
 * setMeetingTime(int day, int hour, int minute, int meetinglength) -- sets start and end time in integer minute format
 * setClubNotes(String notes)
 * getName() - returns name as string
 * getTags() - returns Tags as ArrayList<Tags>
 * getTimeCommitment() - returns int
 * getClubSize() - return club size as int
 * getClubNotes() - returns club notes as string
 * getMeetingTime() - returns int[] meeting time in integer minute format
 * readMeetingTime() - returns int[] time containing day of week, hour, minute, and meeting length
 * timeConflict(ArrayList<int[]> schedule) - returns int minutes of conflict between club and schedule
 * sizeConflict(int desired) - returns double difference in size over desired size
 * tagSatisfied(ArrayList<Tags> desired) - returns percent of tags satisfied
 * commitmentCap() - returns difference between club and user time, negative if club exceeds user commit time
*/
public class Club
{

    //public enum Tags = {Anime, Gaming, Piano};

    public String clubName;
    public int[] clubMeetingTime = new int[2];
    public ArrayList<String> clubTags = new ArrayList<String>();
    public int clubSize;
    public int clubTimeCommitment;
    public String clubNotes;
    public double score;

    public String toString(){
        return getName() + " " + clubMeetingTime[0] + " - " + clubMeetingTime[1] + " " + clubTags.get(0) + " size(" + clubSize + ") timecom(" + clubTimeCommitment + ")";
    }

    Club(){}
    Club(String name)
    {
        setName(name);
    }
    Club(String name, int[] meetingTime, ArrayList<String> tags, int size, int timeCommitment, String notes)
    {
        setName(name);
        clubMeetingTime = meetingTime;
        setTags(tags);
        setClubSize(size);
        setTimeCommitment(timeCommitment);
        setClubNotes(notes);
    }

    void setName(String name)
    {
        clubName = name;
    }

    String getName()
    {
        return clubName;
    }

    void setTags(ArrayList<String> append)
    {
        for(int i = 0; i < append.size(); i++)
        {
            clubTags.add(append.get(i));
        }
    }

    ArrayList<String> getTags()
    {
        return clubTags;
    }

    void setTimeCommitment(int minutes)
    {
        clubTimeCommitment = minutes;
    }

    int getTimeCommitment()
    {
        return clubTimeCommitment;
    }

    void setClubSize(int people)
    {
        clubSize = people;
    }

    int getClubSize()
    {
        return clubSize;
    }

    //converts 
    void setMeetingTime(int day, int hour, int minute, int meetingLength)
    {
        clubMeetingTime[0] = ((day * 24) + hour) * 60 + minute;
        clubMeetingTime[1] = (clubMeetingTime[0] + meetingLength - 1) % 10080;
    }

    //returns meeting time in integer format
    int[] getMeetingTime()
    {
        int[] copy = {clubMeetingTime[0], clubMeetingTime[1]};
        return copy;
    }

    //returns day, hour, minute, and meeting length for the club
    int[] readMeetingTime()
    {
        int day = clubMeetingTime[0]/1440;
        int hour = (clubMeetingTime[0] /60) % 24;
        int minute = clubMeetingTime[0] % 60;
        int[] readable = {day, hour, minute,
                         (clubMeetingTime[1] - clubMeetingTime[0] + 10081) % 10080};
        return readable;
    }

    void setClubNotes(String notes)
    {
        clubNotes = notes;
    }

    String getClubNotes()
    {
        return clubNotes;
    }

    //returns the minutes of conflict between the club and their schedule
    int timeConflict(ArrayList<int[]> schedule)
    {
        int totalMinutes = 0;
        for(int i = 0; i < schedule.size(); i++)
        {
            int curConflict = Math.min(clubMeetingTime[1], schedule.get(i)[1]) - Math.max(clubMeetingTime[0], schedule.get(i)[0]) + 1;
            if(curConflict < 0) curConflict = 0;
            totalMinutes += curConflict;
        }
        return totalMinutes;
    }

    //returns the difference in sizes, divided by the size requested by the user
    double sizeConflict(int desired)
    {
        if(desired == 0) return (double)clubSize;
        return Math.abs((double)desired - clubSize) / desired;
    }

    //returns the percent of tags satisfied
    double tagSatisfied(ArrayList<String> desired)
    //@ensures 0 <= \result && \result <= 1
    {
        if(desired.size() == 0) return (double)1;
        int totalTag = 0;
        for(int i = 0; i < desired.size(); i++)
        {
            for(int j = 0; j < clubTags.size(); j++)
            {
                if(desired.get(i) == clubTags.get(j))
                {
                    totalTag++;
                    break;
                }
            }
        }
        return ((double) totalTag) / desired.size();
    }

    //return int minutes, negative if clubTimeCommitment exceeds their time
    int commitmentCap(int commit)
    {
        return commit - clubTimeCommitment;
    }

    public static ArrayList<Club> loadClubList() throws IOException{
        ArrayList<Club> clublist = new ArrayList<>();
        for(int i = 1; i < 3; i++){
            clublist.add(loadClub("https://raw.githubusercontent.com/LeoAndTheTree/CMUClub/master/Club"+i+".txt"));
        }
        return clublist;
    }

    public static Club loadClub(String directpath) throws IOException
    {
        Club newClub = new Club();
        URL url = new URL(directpath);
        //File dir = new File(directpath);
        if(url != null)
        {
            URLConnection con = url.openConnection();
            InputStream is =con.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            StringTokenizer st;

            newClub.setName(bf.readLine().substring(5));
            st = new StringTokenizer(bf.readLine());

            st.nextToken();
            ArrayList<String> tagList = new ArrayList<String>();
            while(st.hasMoreTokens())
            {
                tagList.add(st.nextToken());
            }
            newClub.setTags(tagList);
            st = new StringTokenizer(bf.readLine());

            st.nextToken(); newClub.setClubSize(Integer.parseInt(st.nextToken()));
            st = new StringTokenizer(bf.readLine());
            
            st.nextToken();
            int[] startend = {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
            newClub.clubMeetingTime=(startend);
            st = new StringTokenizer(bf.readLine());

            st.nextToken(); newClub.setTimeCommitment(Integer.parseInt(st.nextToken()));
            
            newClub.setClubNotes(bf.readLine().substring(6));

            bf.close();
        }
        return newClub;
    }

}
