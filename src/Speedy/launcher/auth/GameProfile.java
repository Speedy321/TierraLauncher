package Speedy.launcher.auth;

public class GameProfile
{

    private final String id;
    private final String name;

    public GameProfile(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }
        GameProfile that = (GameProfile)o;
        if(!id.equals(that.id))
        {
            return false;
        }
        return name.equals(that.name);
    }

    public int hashCode()
    {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public String toString()
    {
        return (new StringBuilder("GameProfile{id='")).append(id).append('\'').append(", name='").append(name).append('\'').append('}').toString();
    }
}
