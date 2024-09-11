-- Bảng student
CREATE TABLE `student` (
  `studentID` INT(11) NOT NULL AUTO_INCREMENT,
  `fullName` VARCHAR(255),
  `address` VARCHAR(255),
  `phone` VARCHAR(20),
  `email` VARCHAR(255),
  `birthDate` DATE,
  `registrationNo` VARCHAR(50),
  `batch` VARCHAR(50),
  `admissionDate` DATE,
  `previousInstitute` VARCHAR(255),
  `reasonForLeaving` VARCHAR(255),
  `guardianContact` VARCHAR(20),
  `profilePicture` VARCHAR(255),
  `classID` INT(11),
  `sex` ENUM('male', 'female'),
  `status` ENUM('active', 'inactive') DEFAULT 'active',
  PRIMARY KEY (`studentID`)
);

-- Bảng staff
CREATE TABLE `staff` (
  `staffID` INT(11) NOT NULL AUTO_INCREMENT,
  `fullName` VARCHAR(255),
  `address` VARCHAR(255),
  `phone` VARCHAR(20),
  `email` VARCHAR(255),
  `birthDate` DATE,
  `department` VARCHAR(100),
  `position` VARCHAR(100),
  `hireDate` DATE,
  `salary` DECIMAL(10,2),
  `lastLogin` DATETIME,
  `profilePicture` VARCHAR(255),
  `sex` ENUM('male', 'female'),
  `status` ENUM('active', 'inactive') DEFAULT 'active',
  PRIMARY KEY (`staffID`)
);

-- Bảng grade
CREATE TABLE `grade` (
  `gradeID` INT(11) NOT NULL AUTO_INCREMENT,
  `studentID` INT(11),
  `staffID` INT(11),
  `subjectID` INT(11),
  `score` DECIMAL(5,2),
  `date` DATE,
  `comment` TEXT,
  PRIMARY KEY (`gradeID`)
);

-- Bảng feedback
CREATE TABLE `feedback` (
  `feedbackID` INT(11) NOT NULL AUTO_INCREMENT,
  `studentID` INT(11),
  `feedbackDate` DATE,
  `feedbackContent` TEXT,
  PRIMARY KEY (`feedbackID`)
);

-- Bảng tuitionfee
CREATE TABLE `tuitionfee` (
  `tuitionID` INT(11) NOT NULL AUTO_INCREMENT,
  `studentID` INT(11),
  `amount` DECIMAL(10,2),
  `payment_status` ENUM('paid', 'unpaid'),
  `payment_date` DATE,
  `description` VARCHAR(255),
  PRIMARY KEY (`tuitionID`)
);

-- Bảng extracurricularactivities
CREATE TABLE `extracurricularactivities` (
  `activityID` INT(11) NOT NULL AUTO_INCREMENT,
  `studentID` INT(11),
  `activityName` VARCHAR(255),
  `participationDate` DATE,
  `role` VARCHAR(100),
  `achievement` VARCHAR(255),
  PRIMARY KEY (`activityID`)
);

-- Bảng subjects
CREATE TABLE `subjects` (
  `subjectID` INT(11) NOT NULL AUTO_INCREMENT,
  `subjectName` VARCHAR(100),
  `subjectDescription` TEXT,
  PRIMARY KEY (`subjectID`)
);

-- Bảng timetable
CREATE TABLE `timetable` (
  `timetableID` INT(11) NOT NULL AUTO_INCREMENT,
  `classID` INT(11),
  `subjectID` INT(11),
  `staffID` INT(11),
  `dayOfWeek` VARCHAR(20),
  `startTime` TIME,
  `endTime` TIME,
  PRIMARY KEY (`timetableID`)
);

-- Bảng classes
CREATE TABLE `classes` (
  `classID` INT(11) NOT NULL AUTO_INCREMENT,
  `className` VARCHAR(100),
  `staffID` INT(11),
  PRIMARY KEY (`classID`)
);

-- Bảng classteacher
CREATE TABLE `classteacher` (
  `classTeacherID` INT(11) NOT NULL AUTO_INCREMENT,
  `staffID` INT(11),
  `classID` INT(11),
  `assignedDate` DATE,
  PRIMARY KEY (`classTeacherID`)
);

-- Bảng staffeducation
CREATE TABLE `staffeducation` (
  `staffID` INT(11),
  `degree` VARCHAR(100),
  `institution` VARCHAR(255),
  `graduationYear` INT(11),
  PRIMARY KEY (`staffID`, `degree`)
);

-- Bảng stafffamilybackground
CREATE TABLE `stafffamilybackground` (
  `staffID` INT(11),
  `spouseName` VARCHAR(255),
  `children` VARCHAR(255),
  `emergencyContact` VARCHAR(255),
  PRIMARY KEY (`staffID`)
);

-- Bảng staffexperience
CREATE TABLE `staffexperience` (
  `staffID` INT(11),
  `previousCompany` VARCHAR(255),
  `jobTitle` VARCHAR(255),
  `yearsOfExperience` INT(11),
  PRIMARY KEY (`staffID`)
);

-- Bảng staffpromotion
CREATE TABLE `staffpromotion` (
  `staffID` INT(11),
  `promotionDate` DATE,
  `newPosition` VARCHAR(100),
  PRIMARY KEY (`staffID`, `promotionDate`)
);

-- Bảng users
CREATE TABLE `users` (
  `userID` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50),
  `password` VARCHAR(255),
  `fullName` VARCHAR(255),
  `email` VARCHAR(255),
  `phone` VARCHAR(20),
  `dateOfBirth` DATE,
  `staffID` INT(11),
  PRIMARY KEY (`userID`)
);

-- Bảng userroles
CREATE TABLE `userroles` (
  `userID` INT(11),
  `roleID` INT(11),
  `assignedDate` DATE,
  PRIMARY KEY (`userID`, `roleID`)
);

-- Bảng role_permissions
CREATE TABLE `role_permissions` (
  `roleID` INT(11),
  `permissionID` INT(11),
  PRIMARY KEY (`roleID`, `permissionID`)
);

-- Bảng permissions
CREATE TABLE `permissions` (
  `permissionID` INT(11) NOT NULL AUTO_INCREMENT,
  `permissionName` VARCHAR(100),
  `permissionDescription` TEXT,
  PRIMARY KEY (`permissionID`)
);

-- Bảng roles
CREATE TABLE `roles` (
  `roleID` INT(11) NOT NULL AUTO_INCREMENT,
  `roleName` VARCHAR(100),
  `roleDescription` TEXT,
  PRIMARY KEY (`roleID`)
);

-- Bảng passwordchange
CREATE TABLE `passwordchange` (
  `changeID` INT(11) NOT NULL AUTO_INCREMENT,
  `userID` INT(11),
  `oldPassword` VARCHAR(255),
  `newPassword` VARCHAR(255),
  `changeDate` DATE,
  PRIMARY KEY (`changeID`),
  FOREIGN KEY (`userID`) REFERENCES `users`(`userID`) ON DELETE CASCADE
);

-- Bảng useractivitylog
CREATE TABLE `useractivitylog` (
  `logID` INT(11) NOT NULL AUTO_INCREMENT,
  `userID` INT(11),
  `activityType` VARCHAR(100),
  `activityDate` DATETIME,
  `activityDetails` TEXT,
  PRIMARY KEY (`logID`)
);
