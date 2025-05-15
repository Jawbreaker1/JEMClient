package JEMClient;

public class JEMClientUserImage {

	private String imagePath;
	private String imageName;
	
	public JEMClientUserImage(String name, String path)
	{
		imageName = name;
		imagePath = path;
	}
	
	public void setImagePath(String newPath)
	{
		imagePath = newPath;
	}
	
	public String getImagePath()
	{
		return imagePath;
	}
	
	public void setImageName(String newName)
	{
		imageName = newName;
	}
	
	public String getImageName()
	{
		return imageName;
	}
}
