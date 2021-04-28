USE master;
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'EmployeeMileageApp')
    DROP DATABASE EmployeeMileageApp;
GO

CREATE DATABASE EmployeeMileageApp;
GO

USE EmployeeMileageApp;
GO

CREATE TABLE Users
(
    id INT IDENTITY(1, 1) PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    password NVARCHAR(MAX) NOT NULL,
    usertypeid INT NOT NULL,
    databasePrincipalId INT,
    deleteind CHAR(1) NOT NULL,
    createtimestamp DATETIME NOT NULL,
    modifytimestamp DATETIME NOT NULL,
    createdby INT NOT NULL,
    modifiedby INT NOT NULL
);

CREATE TABLE UserType
(
    id INT IDENTITY(1, 1) PRIMARY KEY,
    description VARCHAR(30) NOT NULL,
    deleteind CHAR(1) NOT NULL,
    createtimestamp DATETIME NOT NULL,
    modifytimestamp DATETIME NOT NULL,
    createdby INT NOT NULL,
    modifiedby INT NOT NULL
);

CREATE TABLE Department
(
    id INT IDENTITY(1, 1) PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    deleteind CHAR(1) NOT NULL,
    createtimestamp DATETIME NOT NULL,
    modifytimestamp DATETIME NOT NULL,
    createdby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL,
    modifiedby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL
);

CREATE TABLE Employee
(
    employeenumber NVARCHAR(30) PRIMARY KEY,
    firstname VARCHAR(40) NOT NULL,
    lastname VARCHAR(40) NOT NULL,
    middlename VARCHAR(40) NULL,
    emailaddress VARCHAR(50) NULL,
    userid INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL,
    departmentid INT
        FOREIGN KEY REFERENCES dbo.Department (id) NULL,
    deleteind CHAR(1) NOT NULL,
    createtimestamp DATETIME NOT NULL,
    modifytimestamp DATETIME NOT NULL,
    createdby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL,
    modifiedby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL
);

CREATE TABLE Location
(
    id INT IDENTITY(1, 1) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address1 VARCHAR(50) NOT NULL,
    address2 VARCHAR(50) NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip INT NOT NULL,
    deleteind CHAR(1) NOT NULL,
    createtimestamp DATETIME NOT NULL,
    modifytimestamp DATETIME NOT NULL,
    createdby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL,
    modifiedby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL
);

CREATE TABLE MileageRate
(
    id INT IDENTITY(1, 1) PRIMARY KEY,
    year VARCHAR(4) NOT NULL,
    rate MONEY NOT NULL,
    deleteind CHAR(1) NOT NULL,
    createtimestamp DATETIME NOT NULL,
    modifytimestamp DATETIME NOT NULL,
    createdby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL,
    modifiedby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL
);

CREATE TABLE MileageEntry
(
    id INT IDENTITY(1, 1) PRIMARY KEY,
    date VARCHAR(10) NOT NULL,
    userid INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL,
    startlocationid INT
        FOREIGN KEY REFERENCES dbo.Location (id) NOT NULL,
    endlocationid INT
        FOREIGN KEY REFERENCES dbo.Location (id) NOT NULL,
    workorderid INT NULL,
    workorderdescription VARCHAR(50) NULL,
    calculatedmiles INT NOT NULL,
    rateid INT
        FOREIGN KEY REFERENCES dbo.MileageRate (id) NOT NULL,
    deleteind CHAR(1) NOT NULL,
    createtimestamp DATETIME NOT NULL,
    modifytimestamp DATETIME NOT NULL,
    createdby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL,
    modifiedby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL
);
GO

CREATE TABLE Distance
(
    id INT IDENTITY(1, 1) PRIMARY KEY,
    startlocationid INT
        FOREIGN KEY REFERENCES dbo.Location (id) NOT NULL,
    endlocationid INT
        FOREIGN KEY REFERENCES dbo.Location (id) NOT NULL,
    miles INT NOT NULL,
    deleteind CHAR(1) NOT NULL,
    createtimestamp DATETIME NOT NULL,
    modifytimestamp DATETIME NOT NULL,
    createdby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL,
    modifiedby INT
        FOREIGN KEY REFERENCES dbo.Users (id) NOT NULL
);
GO

CREATE ROLE normal;
CREATE ROLE admin;
GO

GRANT SELECT, UPDATE, INSERT, DELETE ON Department TO admin;
GRANT SELECT, UPDATE, INSERT, DELETE ON Distance TO admin;
GRANT SELECT, UPDATE, INSERT, DELETE ON Employee TO admin;
GRANT SELECT, UPDATE, INSERT, DELETE ON Location TO admin;
GRANT SELECT, UPDATE, INSERT, DELETE ON MileageEntry TO admin;
GRANT SELECT, UPDATE, INSERT, DELETE ON MileageRate TO admin;
GRANT SELECT, UPDATE, INSERT, DELETE ON Users TO admin;
GRANT SELECT, UPDATE, INSERT, DELETE ON UserType TO admin;

GRANT SELECT, UPDATE, INSERT, DELETE ON MileageEntry TO normal;
GRANT SELECT ON Department TO normal;
GRANT SELECT ON Distance TO normal;
GRANT SELECT ON Location TO normal;
GRANT SELECT ON MileageRate TO normal;
GRANT SELECT ON Department TO normal;
GO

INSERT INTO dbo.UserType
(
    description,
    deleteind,
    createtimestamp,
    modifytimestamp,
    createdby,
    modifiedby
)
VALUES
('normal', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1),
('admin', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1);
GO

CREATE USER gday WITHOUT LOGIN;
ALTER ROLE admin ADD MEMBER gday;
INSERT INTO dbo.Users
(
    username,
    password,
    deleteind,
    databasePrincipalId,
    createtimestamp,
    modifytimestamp,
    createdby,
    modifiedby,
    usertypeid
)
VALUES
('gday', HASHBYTES('SHA2_512', 'password123'), 'N', DATABASE_PRINCIPAL_ID('gday'), CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP, 1, 1, 1);
GO


CREATE USER jsmith WITHOUT LOGIN;
ALTER ROLE normal ADD MEMBER jsmith;
INSERT INTO dbo.Users
(
    username,
    password,
    deleteind,
    databasePrincipalId,
    createtimestamp,
    modifytimestamp,
    createdby,
    modifiedby,
    usertypeid
)
VALUES
('jsmith', HASHBYTES('SHA2_512', 'password123'), 'N', DATABASE_PRINCIPAL_ID('jsmith'), CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP, 1, 1, 1);
GO

INSERT INTO dbo.MileageRate
(
    year,
    rate,
    deleteind,
    createtimestamp,
    modifytimestamp,
    createdby,
    modifiedby
)
VALUES
('2021', 2.25, 'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1);
GO

ALTER TABLE dbo.Users
ADD
    FOREIGN KEY (usertypeid) REFERENCES dbo.UserType (id);
GO

ALTER TABLE dbo.Users
ADD
    FOREIGN KEY (createdby) REFERENCES dbo.Users (id);
GO

ALTER TABLE dbo.Users
ADD
    FOREIGN KEY (modifiedby) REFERENCES dbo.Users (id);
GO

ALTER TABLE dbo.UserType
ADD
    FOREIGN KEY (createdby) REFERENCES dbo.Users (id);
GO

ALTER TABLE dbo.UserType
ADD
    FOREIGN KEY (modifiedby) REFERENCES dbo.Users (id);
GO

ALTER TABLE dbo.Employee ADD CHECK (emailaddress LIKE '%_@__%.__%');

ALTER TABLE dbo.Users ADD CHECK (username NOT LIKE '%[^a-zA-Z0-9]%');

ALTER TABLE dbo.Department ADD CHECK (name NOT LIKE '%[^a-zA-Z]%');

ALTER TABLE dbo.MileageRate ADD CHECK (rate > 0);

ALTER TABLE dbo.MileageEntry ADD CHECK (calculatedmiles > 0);

ALTER TABLE dbo.Distance ADD CHECK (miles > 0)

ALTER TABLE dbo.Location ADD CHECK (city NOT LIKE '%[^a-zA-Z0-9 ]%');

ALTER TABLE dbo.Location ADD CHECK (state NOT LIKE '%[^a-zA-Z0-9 ]%');

ALTER TABLE dbo.Location ADD CHECK (zip NOT LIKE '%[^a-zA-Z0-9]%')

INSERT INTO dbo.Department
(
    name,
    deleteind,
    createtimestamp,
    modifytimestamp,
    createdby,
    modifiedby
)
VALUES
('Development', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1),
('IT', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1);

INSERT INTO dbo.Employee
(
    userid,
    firstname,
    lastname,
    middlename,
    emailaddress,
    employeenumber,
    departmentid,
    deleteind,
    createtimestamp,
    modifytimestamp,
    createdby,
    modifiedby
)
VALUES
(1, 'Gerry', 'Day', '', 'gday@wkhs.com', 'G12345', 1, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1);
GO


CREATE PROCEDURE spAddUser
(
    @username VARCHAR(20),
    @password NVARCHAR(MAX),
    @usertype VARCHAR(10),
    @createdby INT
)
WITH ENCRYPTION
AS
BEGIN
    --IF @usertype = 'admin'
    --BEGIN
    --	CREATE USER @username WITHOUT LOGIN
    --	ALTER ROLE admin ADD MEMBER @username
    --END

    INSERT INTO dbo.Users
    (
        username,
        password,
        usertypeid,
        databasePrincipalId,
        deleteind,
        createtimestamp,
        modifytimestamp,
        createdby,
        modifiedby
    )
    VALUES
    (   @username, HASHBYTES('SHA2_512', @password),
        (
            SELECT id FROM UserType WHERE description = @usertype
        ), DATABASE_PRINCIPAL_ID(@username), 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdby, @createdby);
END;
GO

CREATE PROCEDURE spUpdateUser
(
    @userid INT,
    @username VARCHAR(20),
    @password NVARCHAR(MAX),
    @usertypeid VARCHAR(20),
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.Users
    SET username = @username,
        password = HASHBYTES('SHA2_512', @password),
        usertypeid =
        (
            SELECT id FROM UserType WHERE description = @usertypeid
        ),
        modifytimestamp = CURRENT_TIMESTAMP,
        modifiedby = @modifiedby
    WHERE id = @userid;
END;
GO

CREATE PROCEDURE spDeleteUser
(
    @userid INT,
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.Users
    SET deleteind = 'Y',
        modifiedby = @modifiedby,
        modifytimestamp = CURRENT_TIMESTAMP
    WHERE id = @userid;
END;
GO

CREATE PROCEDURE spAddUserType
(
    @description VARCHAR(30),
    @createdby INT
)
WITH ENCRYPTION
AS
BEGIN
    INSERT INTO dbo.UserType
    (
        description,
        deleteind,
        createtimestamp,
        modifytimestamp,
        createdby,
        modifiedby
    )
    VALUES
    (@description, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdby, @createdby);
END;
GO

CREATE PROCEDURE spUpdateUserType
(
    @id INT,
    @description VARCHAR(30),
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.UserType
    SET description = @description,
        modifytimestamp = CURRENT_TIMESTAMP,
        modifiedby = @modifiedby
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spDeleteUserType
(@id INT)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.UserType
    SET deleteind = 'Y'
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spAddDepartment
(
    @name VARCHAR(30),
    @createdby INT
)
WITH ENCRYPTION
AS
BEGIN
    INSERT INTO dbo.Department
    (
        name,
        deleteind,
        createtimestamp,
        modifytimestamp,
        createdby,
        modifiedby
    )
    VALUES
    (@name, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdby, @createdby);
END;
GO

CREATE PROCEDURE spUpdateDepartment
(
    @id INT,
    @name VARCHAR(30),
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.Department
    SET name = @name,
        modifytimestamp = CURRENT_TIMESTAMP,
        modifiedby = @modifiedby
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spDeleteDepartment
(
    @id INT,
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.Department
    SET deleteind = 'Y',
        modifiedby = @modifiedby,
        modifytimestamp = CURRENT_TIMESTAMP
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spAddEmployee
(
    @employeenumber VARCHAR(30),
    @firstname VARCHAR(30),
    @lastname VARCHAR(30),
    @middlename VARCHAR(30),
    @emailaddress VARCHAR(50),
    @department VARCHAR(50),
    @userid INT,
    @createdby INT
)
WITH ENCRYPTION
AS
BEGIN
    INSERT INTO dbo.Employee
    (
        employeenumber,
        firstname,
        lastname,
        middlename,
        emailaddress,
        departmentid,
        userid,
        deleteind,
        createtimestamp,
        modifytimestamp,
        createdby,
        modifiedby
    )
    VALUES
    (   @employeenumber, @firstname, @lastname, @middlename, @emailaddress,
        (
            SELECT id FROM Department WHERE name = @department
        ), @userid, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdby, @createdby);
END;
GO

CREATE PROCEDURE spUpdateEmployee
(
    @employeenumber VARCHAR(30),
    @firstname VARCHAR(30),
    @lastname VARCHAR(30),
    @middlename VARCHAR(30),
    @userid INT,
    @emailaddress VARCHAR(50),
    @department VARCHAR(50),
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.Employee
    SET firstname = @firstname,
        lastname = @lastname,
        middlename = @middlename,
        userid = @userid,
        emailaddress = @emailaddress,
        departmentid =
        (
            SELECT id FROM Department WHERE name = @department
        ),
        modifytimestamp = CURRENT_TIMESTAMP,
        modifiedby = @modifiedby
    WHERE employeenumber = @employeenumber;
END;
GO

CREATE PROCEDURE spDeleteEmployee
(
    @employeenumber VARCHAR(30),
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.Employee
    SET deleteind = 'Y',
        modifiedby = @modifiedby,
        modifytimestamp = CURRENT_TIMESTAMP
    WHERE employeenumber = @employeenumber;
END;
GO

CREATE PROCEDURE spAddLocation
(
    @name VARCHAR(30),
    @address1 VARCHAR(30),
    @address2 VARCHAR(30),
    @city VARCHAR(30),
    @state VARCHAR(30),
    @zip VARCHAR(30),
    @createdby INT
)
WITH ENCRYPTION
AS
BEGIN
    INSERT INTO Location
    (
        name,
        address1,
        address2,
        city,
        state,
        zip,
        deleteind,
        createtimestamp,
        modifytimestamp,
        createdby,
        modifiedby
    )
    VALUES
    (@name, @address1, @address2, @city, @state, @zip, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdby,
     @createdby);
END;
GO

CREATE PROCEDURE spUpdateLocation
(
    @id INT,
    @name VARCHAR(30),
    @address1 VARCHAR(30),
    @address2 VARCHAR(30),
    @city VARCHAR(30),
    @state VARCHAR(30),
    @zip VARCHAR(30),
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE Location
    SET name = @name,
        @address1 = @address1,
        address2 = @address2,
        city = @city,
        state = @state,
        zip = @zip,
        modifiedby = @modifiedby,
        modifytimestamp = CURRENT_TIMESTAMP
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spDeleteLocation
(
    @id INT,
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE Location
    SET deleteind = 'Y',
        modifiedby = @modifiedby,
        modifytimestamp = CURRENT_TIMESTAMP
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spAddMileageRate
(
    @year INT,
    @rate DECIMAL,
    @createdby INT
)
WITH ENCRYPTION
AS
BEGIN
    INSERT INTO dbo.MileageRate
    (
        year,
        rate,
        deleteind,
        createtimestamp,
        modifytimestamp,
        createdby,
        modifiedby
    )
    VALUES
    (@year, @rate, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdby, @createdby);
END;
GO

CREATE PROCEDURE spUpdateMileageRate
(
    @id INT,
    @rate DECIMAL,
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.MileageRate
    SET rate = @rate,
        modifytimestamp = CURRENT_TIMESTAMP,
        modifiedby = @modifiedby
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spDeleteMileageRate
(
    @id INT,
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.MileageRate
    SET deleteind = 'Y',
        modifiedby = @modifiedby,
        modifytimestamp = CURRENT_TIMESTAMP
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spDeleteMileageEntry
(
    @id INT,
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.MileageEntry
    SET deleteind = 'Y',
        modifiedby = @modifiedby,
        modifytimestamp = CURRENT_TIMESTAMP
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spAddMileageEntry
(
    @userid INT,
    @date VARCHAR(10),
    @startlocation VARCHAR(50),
    @endlocation VARCHAR(50),
    @workorderid INT,
    @workorderdescription VARCHAR(50),
    @rateid INT,
    @createdby INT
)
WITH ENCRYPTION
AS
BEGIN

    DECLARE @miles AS INT;
    SET @miles =
    (
        SELECT D.miles
        FROM Distance D
            INNER JOIN Location L
                ON L.id = D.startlocationid
            INNER JOIN Location L1
                ON L1.id = D.endlocationid
        WHERE D.startlocationid =
        (
            SELECT id FROM Location WHERE name = @startlocation
        )
              AND D.endlocationid =
              (
                  SELECT id FROM Location WHERE name = @endlocation
              )
    );
    INSERT INTO dbo.MileageEntry
    (
        userid,
        date,
        startlocationid,
        endlocationid,
        workorderid,
        workorderdescription,
        calculatedmiles,
        rateid,
        deleteind,
        createtimestamp,
        modifytimestamp,
        createdby,
        modifiedby
    )
    VALUES
    (   @userid, @date,
        (
            SELECT id FROM Location WHERE name = @startlocation
        ),
        (
            SELECT id FROM Location WHERE name = @endlocation
        ), @workorderid, @workorderdescription, @miles, @rateid, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdby,
        @createdby);
END;
GO

CREATE PROCEDURE spUpdateMileageEntry
(
    @id INT,
    @date VARCHAR(10),
    @startlocation VARCHAR(50),
    @endlocation VARCHAR(50),
    @workorderid INT,
    @workorderdescription VARCHAR(50),
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE dbo.MileageEntry
    SET date = @date,
        startlocationid =
        (
            SELECT id FROM Location WHERE name = @startlocation
        ),
        endlocationid =
        (
            SELECT id FROM Location WHERE name = @endlocation
        ),
        workorderid = @workorderid,
        workorderdescription = @workorderdescription,
        modifiedby = @modifiedby,
        modifytimestamp = CURRENT_TIMESTAMP
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spGetDepartment
(@id INT)
WITH ENCRYPTION
AS
BEGIN
    SELECT id,
           name
    FROM Department
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spGetDepartments
WITH ENCRYPTION
AS
BEGIN
    SELECT id,
           name
    FROM Department
    WHERE deleteind <> 'Y';
END;
GO

CREATE PROCEDURE spGetEmployees
WITH ENCRYPTION
AS
BEGIN
    SELECT E.employeenumber,
           E.firstname,
           E.lastname,
           E.middlename,
           E.emailaddress,
           ISNULL(D.name, '') AS department,
           E.userid
    FROM Employee E
        LEFT OUTER JOIN Department D
            ON D.id = E.departmentid
    WHERE E.deleteind <> 'Y';
END;
GO

CREATE PROCEDURE spGetEmployee
(@employeenumber VARCHAR(50))
WITH ENCRYPTION
AS
BEGIN
    SELECT E.employeenumber,
           E.firstname,
           E.lastname,
           E.middlename,
           E.emailaddress,
           ISNULL(D.name, '') AS department,
           E.userid
    FROM Employee E
        LEFT OUTER JOIN Department D
            ON D.id = E.departmentid
    WHERE E.deleteind <> 'Y'
          AND E.employeenumber = @employeenumber;
END;
GO

CREATE PROCEDURE spGetMileageRate
WITH ENCRYPTION
AS
BEGIN
    SELECT *
    FROM MileageRate
    WHERE deleteind <> 'Y';
END;
GO

CREATE PROCEDURE spGetLocations
WITH ENCRYPTION
AS
BEGIN
    SELECT id,
           name,
           address1,
           address2,
           city,
           state,
           zip
    FROM Location
    WHERE deleteind <> 'Y';
END;
GO

CREATE PROCEDURE spGetLocation
(@id INT)
WITH ENCRYPTION
AS
BEGIN
    SELECT id,
           name,
           address1,
           address2,
           city,
           state,
           zip
    FROM Location
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spGetMileageEntry
(@id INT)
WITH ENCRYPTION
AS
BEGIN
    SELECT ME.id,
           ME.date,
           L.name AS startlocation,
           L1.name AS endlocation,
           ME.workorderid,
           ME.workorderdescription,
           ME.calculatedmiles AS miles,
           ME.rateid AS rate
    FROM MileageEntry ME
        INNER JOIN Location L
            ON L.id = ME.startlocationid
        INNER JOIN Location L1
            ON L1.id = ME.endlocationid
    WHERE ME.id = @id
          AND ME.deleteind <> 'Y';
END;
GO

CREATE PROCEDURE spGetMileageEntries
(@userid INT)
WITH ENCRYPTION
AS
BEGIN
    SELECT ME.id,
           ME.date,
           L.name AS startlocation,
           L1.name AS endlocation,
           ME.workorderid,
           ME.workorderdescription,
           ME.calculatedmiles AS miles,
           MR.rate AS rate
    FROM MileageEntry ME
        INNER JOIN Location L
            ON L.id = ME.startlocationid
        INNER JOIN Location L1
            ON L1.id = ME.endlocationid
        INNER JOIN MileageRate MR
            ON MR.id = ME.rateid
    WHERE ME.userid = 1
          AND ME.deleteind <> 'Y';
END;
GO

CREATE PROCEDURE spGetUsers
WITH ENCRYPTION
AS
BEGIN
    SELECT U.id,
           U.username,
           UT.description AS usertype
    FROM Users U
        INNER JOIN UserType UT
            ON UT.id = U.usertypeid
    WHERE U.deleteind <> 'Y';
END;
GO

CREATE PROCEDURE spAddDistance
(
    @startlocation VARCHAR(50),
    @endlocation VARCHAR(50),
    @miles INT,
    @createdby INT
)
WITH ENCRYPTION
AS
BEGIN
    INSERT INTO dbo.Distance
    (
        startlocationid,
        endlocationid,
        miles,
        deleteind,
        createtimestamp,
        modifytimestamp,
        createdby,
        modifiedby
    )
    VALUES
    (
        (
            SELECT id FROM Location WHERE name = @startlocation
        ),
        (
            SELECT id FROM Location WHERE name = @endlocation
        ), @miles, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdby, @createdby);
END;
GO

CREATE PROCEDURE spGetDistance
(@id INT)
WITH ENCRYPTION
AS
BEGIN
    SELECT D.id,
           L.name AS startLocation,
           L1.name AS endLocation,
           D.miles
    FROM Distance D
        INNER JOIN Location L
            ON L.id = D.startlocationid
        INNER JOIN Location L1
            ON L1.id = D.endlocationid
    WHERE D.id = @id;
END;
GO

CREATE PROCEDURE spGetDistances
WITH ENCRYPTION
AS
BEGIN
    SELECT D.id,
           L.name AS startlocation,
           L1.name AS endlocation,
           D.miles
    FROM Distance D
        INNER JOIN Location L
            ON L.id = D.startlocationid
        INNER JOIN Location L1
            ON L1.id = D.endlocationid
    WHERE D.deleteind <> 'Y';
END;
GO

CREATE PROCEDURE spUpdateDistance
(
    @id INT,
    @miles INT,
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE Distance
    SET miles = @miles,
        modifytimestamp = CURRENT_TIMESTAMP,
        modifiedby = @modifiedby
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spDeleteDistance
(
    @id INT,
    @modifiedby INT
)
WITH ENCRYPTION
AS
BEGIN
    UPDATE Distance
    SET deleteind = 'Y',
        modifiedby = @modifiedby,
        modifytimestamp = CURRENT_TIMESTAMP
    WHERE id = @id;
END;
GO

CREATE PROCEDURE spGetUserTypes
WITH ENCRYPTION
AS
BEGIN
    SELECT DISTINCT
           description AS userType
    FROM UserType;
END;
GO

CREATE PROCEDURE spGetUser
(@id INT)
WITH ENCRYPTION
AS
BEGIN
    SELECT U.username,
           U.password,
           UT.description AS usertype
    FROM Users U
        INNER JOIN UserType UT
            ON UT.id = U.usertypeid
    WHERE U.id = @id;
END;
GO

CREATE PROCEDURE spCalculateMileage
(@id INT)
WITH ENCRYPTION
AS
BEGIN
    SELECT SUM(calculatedmiles)
    FROM MileageEntry
    WHERE userid = @id
          AND deleteind <> 'Y';
END;
GO

CREATE PROCEDURE spCalculateReimbursement
(@id INT)
WITH ENCRYPTION
AS
BEGIN
    SELECT SUM(ME.calculatedmiles) * MR.rate AS reimbursement
    FROM MileageEntry ME
        INNER JOIN MileageRate MR
            ON MR.id = ME.rateid
    WHERE ME.userid = @id
          AND ME.deleteind <> 'Y'
    GROUP BY MR.rate;
END;
GO