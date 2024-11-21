public class Section{
public String courseName;
public Student[]list;
public int size;

public Section(String n, int s){
courseName= n;
size=s;

list= new Student[size];

for(int i=0; i<size ; i++)
list[i]= new Student();
}

public void display(){
System.out.println("Section name is = " +courseName);
System.out.println("size : "+size);
System.out.println("Students info= ");

for (int i =0 ; i < size ; i++)
list[i].display();

}
}