# ticket-service
  Ticket Service application is used to perform following three operations.
    1. find available no of seats in the theater
    2. find and hold seats for a given customer
    3. find and reserve seats for a given customer
    
 # Assumptions made in the requirements of this project
    1. Seats are allocated from top row to first row.
    2. Seats are allocated first in the top row then if it not available in top row then seats are allocated in the row below.
    3. No databases are used.
    4. Internal datastructures are used for POC.
    
 # Design considerations
    1. Scanning all the seats for a given customer request is inefficient. so added a noOfAvaialble seats parameter at row level and theater object level. This way we can find noOfAavailable seats when request is made with out scanning all the seats.
    2. while allocating seats for a customer we check all the rows noOfSeats before scanning seats in that row.
    3. Time Complexity for finding available no of seats - O(1)
    4. Time Complexity for finding and holding seats for a given customer - O(n). n is no of rows in a theater.
    5. Time Complexity for finding and reserving seats for a given customer - O(n). n is no of rows in theater.
    6. Spring async annotation is used for running a separate thread and add hold seats back to theater matrix if customer is not reserving those hold seats in a allocated time period.
    7. properties are externalized from the application
    
 # Testing
    1. project is unit tested using Junit framework and mockito mocking framework. code coverage for this project is 96%.
    2. Integration testing of the project is done and testcase is available in test folder. Used spring integration testing frameowork.
    
 # Integration Testing Client file
    TicketServiceImplIntTest is located in pacakge com.walmartlabs.ticketbooking in test folder.
    
 # Building the project
    1. clone the project to developer workstation
        git clone 
    2. building the project
        a. cd ticket-service
        b. mvn clean install
    3. Run the project
        a. java -jar target/ticket-service-0.0.1-SNAPSHOT.jar
        
  # Curl commands for invoking the service
    1. finding no of available seats
    ### Request
      curl http://localhost:8080/ticket-service/available
    ### Response
      300
      
    2. finding and holding seats
    ### Request
    curl -X POST \
    http://localhost:8080/ticket-service/holdSeats \
     -H 'cache-control: no-cache' \
     -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
     -F numSeats=10 \
     -F customerEmail=ravinderuppala@gmail.com
     ### Response
     {"seatHoldId":1978068869,"customerEmailId":"ravinderuppala@gmail.com"}
     
     3. reserving seats
     pass the seatHoldId from holdSeats response to reserveSeats input
     ### Request:
     curl -X POST \
     http://localhost:8080/ticket-service/reserveSeats \
     -H 'cache-control: no-cache' \
     -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
     -F seatHoldId=879105000 \
     -F customerEmail=ravinderuppala@gmail.com
     ### Response
     a3ff84c3-37d2-4ddb-8707-96e427ce277f
     
     
     
    
        
