This is an iPhone app for a City Council that empowers residents to be the City's "eyes and ears." The citizens can reports several neighbourhood issues like pothole, bridge repair, gutter repair etc. to the City Council via this app. The app captures the issue type, GPS coordinates of the location, reporter details etc. and send it across to the City server. 

When the server receiver a new request, it creates a new entry in its database where are the reports are stored with all kinds of information related to the request. It consists of fields like issue code, the date and time at which it was created, the department which the issue type falls under, supervisor code, employee code assigned to resolve the request, current status of request, request id, etc.

A Request ID is a unique identifier generated by the server and assigned to a request. So when a reporter creates a request and send it to the server, the server replies back with the Request ID generated for that request. Of all the information related to a request, some of them are stored on the user side i.e. on the iPhone. These are address of the location (geocoded from the GPS coordinates), type of issue, request id and reporter details submitted with the request. 

The field “Status” of the report is updated by the server administrator depending on the state of the request. Several other fields like employee assigned, vehicle assigned etc are internal to the City council and are all taken care by the server database administrator. When a user wants to check the current status of a request, the app sends a GET request to the server along with the request ID and the server replies with the current status of the request like “For Review”, “Completed” or “Invalid Request” etc. which is displayed to the user on the app. A request is deleted from the database 7 days after its declared Invalid or Complete. 

Relational Schema

REQUEST (RequestId, Address, ReporterName, ProblemType, Status)

PROBLEM_TYPE (Problem_Code, DId)

DEVICE (IMEI_Number, ReportnerName, ReporterPhoneNo)
DEPT (DeptId, SupervisorId, VehicleId)
SUPERVISOR (SupervisorId, SName, DId)
VEHICLE (VehiclePlateNo, DId, BOOL_Available)
WORKER (EmpId, DId, EmpName)
