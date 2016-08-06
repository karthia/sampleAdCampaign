# Sample AdCampaign Application with Rest Service

To Run this application 
- mvn clean spring-boot:run
- Use below urls to access the rest service
-- POST http://localhost:8080/ad 
    {
        "partner_id" :"partner1",
        "duration" :"200000",
        "ad_content":"Muthusamy"
    }
-- GET http://localhost:8080/ad/{partnerId}

To Run Junit tests
- mvn clean test

Thanks.
