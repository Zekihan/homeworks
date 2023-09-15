package tube;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.*;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

public class XMLFormatter{


    public XMLFormatter() {
    }

    public String toFormat(Map<String, User> user){

        Users users = new Users(new ArrayList<>(user.values()));
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        xstream.omitField(Observable.class,"changed");
        xstream.omitField(Observable.class,"obs");
        xstream.alias("user", User.class);
        xstream.alias("users", Users.class);
        xstream.alias("watchlist", Watchlist.class);
        xstream.addImplicitCollection(Users.class, "users");
        StringWriter stringWriter = new StringWriter();
        xstream.marshal(users, new PrettyPrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public XStream createXStream() {
        XStream xstream = new XStream(new StaxDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out, "    ");
            }
        }) {
            // only register the converters we need; other converters generate a private access warning in the console on Java9+...
            @Override
            protected void setupConverters() {
                registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
                registerConverter(new IntConverter(), PRIORITY_NORMAL);
                registerConverter(new FloatConverter(), PRIORITY_NORMAL);
                registerConverter(new DoubleConverter(), PRIORITY_NORMAL);
                registerConverter(new LongConverter(), PRIORITY_NORMAL);
                registerConverter(new ShortConverter(), PRIORITY_NORMAL);
                registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
                registerConverter(new ByteConverter(), PRIORITY_NORMAL);
                registerConverter(new StringConverter(), PRIORITY_NORMAL);
                registerConverter(new DateConverter(), PRIORITY_NORMAL);
                registerConverter(new CollectionConverter(getMapper()), PRIORITY_NORMAL);
                registerConverter((new ReflectionConverter(getMapper(), getReflectionProvider()){
                    @Override
                    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
                        Object result = new Users(new ArrayList<>());
                        result = this.doUnmarshal(result, reader, context);
                        return this.serializationMembers.callReadResolve(result);
                    }

                    @Override
                    public boolean canConvert(Class type) {
                        return type.equals(Users.class);
                    }
                }),PRIORITY_VERY_LOW);
                registerConverter((new ReflectionConverter(getMapper(), getReflectionProvider()){
                    @Override
                    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
                        Object result = new User();
                        result = this.doUnmarshal(result, reader, context);
                        return this.serializationMembers.callReadResolve(result);
                    }
                    @Override
                    public boolean canConvert(Class type) {
                        return type.equals(User.class);
                    }
                }),PRIORITY_VERY_LOW);
                registerConverter((new ReflectionConverter(getMapper(), getReflectionProvider()){
                    @Override
                    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
                        Object result = new Watchlist();
                        result = this.doUnmarshal(result, reader, context);
                        return this.serializationMembers.callReadResolve(result);
                    }
                    @Override
                    public boolean canConvert(Class type) {
                        return type.equals(Watchlist.class);
                    }
                }),PRIORITY_VERY_LOW);
            }
        };

        xstream.addPermission(NoTypePermission.NONE);
        xstream.addPermission(ArrayTypePermission.ARRAYS);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xstream.addPermission(NullPermission.NULL);
        xstream.allowTypeHierarchy(Collection.class);
        xstream.addPermission(new WildcardTypePermission(new String[]{
                User.class.getName(),Users.class.getName(),Watchlist.class.getName(),
                "java.lang.*"
        }));
        xstream.autodetectAnnotations(true);
        xstream.alias("user", User.class);
        xstream.alias("watchlist", Watchlist.class);
        xstream.alias("users", Users.class);
        xstream.addImplicitCollection(Users.class, "users");

        return xstream;
    }

    public Map<String,User> toObject(String data){
        XStream xstream = createXStream();
        Users users = (Users)xstream.fromXML(data);
        Map<String,User> userMap = new HashMap<>();
        for(User user : users.getUsers()){
            userMap.put(user.getUsername(), user);
        }
        return userMap;
    }

    public static void main(String[] args) throws JAXBException, IOException {

        List<Integer> liked = new ArrayList<>();
        List<Integer> disliked = new ArrayList<>();
        liked.add(1);
        liked.add(2);
        disliked.add(3);

        List<Integer> videos = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Ali", "OLAMAAZ"));
        comments.add(new Comment("Ali", "Merhaba Vidyonu vidomda gosterebilirmiyim sagul"));
        videos.add((new Video(1,"video1","content1",new Date(), 0,0,comments).getId()));
        videos.add((new Video(2,"video2","content2",new Date(), 0,0,comments)).getId());
        videos.add((new Video(3,"video3","content3",new Date(), 0,0,comments)).getId());

        Watchlist watchlist = new Watchlist(new ArrayList<>(), "playlist1");
        watchlist.add((new Video(1,"video1","content1",new Date(), 0,0,comments).getId()));
        watchlist.add((new Video(2,"video2","content2",new Date(), 0,0,comments).getId()));

        Watchlist watchlist1 = new Watchlist(new ArrayList<>(), "playlist2");
        watchlist1.add((new Video(3,"video3","content3",new Date(), 0,0,comments)).getId());

        List<Watchlist> watchlists = new ArrayList<>();
        watchlists.add(watchlist);
        List<Watchlist> watchlists1 = new ArrayList<>();
        watchlists.add(watchlist1);

        User user2 = new User("kaan","123", new ArrayList<>(), new ArrayList<>(), liked, disliked, watchlists);
        User user3 = new User("efecan","123",new ArrayList<>(),new ArrayList<>(),liked,disliked,new ArrayList<>());
        User user4 = new User("zekihan","123",new ArrayList<>(),new ArrayList<>(),liked,disliked,watchlists1);
        User user5 = new User("gayegemen","123",new ArrayList<>(),new ArrayList<>(),liked,disliked,new ArrayList<>());
        List<User> users = new ArrayList<>();
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        XMLFormatter xmlFormatter = new XMLFormatter();
        IStorage storage = new FileStorage("users.xml");

        Map<String,User> usersMap =new HashMap<>();
        usersMap.put(user2.getUsername(),user2);
        usersMap.put(user3.getUsername(),user3);
        usersMap.put(user4.getUsername(),user4);
        usersMap.put(user5.getUsername(),user5);
        String asd = xmlFormatter.toFormat(usersMap);
        storage.save(asd);

        System.out.println(storage.read());

        Map<String,User> users1 = xmlFormatter.toObject(storage.read());
        for(User u : users1.values()){
            System.out.println("Username: " + u.getUsername());
            System.out.println("Password: " + u.getPassword());
        }
    }

    private static class Users {
        private List<User> users;

        public Users(List<User> users){ this.users = users; }

        public List<User> getUsers(){ return this.users; }

    }

}