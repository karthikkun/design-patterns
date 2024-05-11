package observer;

public class Main {
    public static void main(String[] args) {
        JobBoard jobBoard = new JobBoard();

        JobSeeker johnDoe = new JobSeeker("John Doe");
        JobSeeker janeDoe = new JobSeeker("Jane Doe");

        jobBoard.attach(johnDoe);
        jobBoard.attach(janeDoe);

        jobBoard.addJobPosting(new JobPost("Software Development Engineer II at AWS"));
    }
}