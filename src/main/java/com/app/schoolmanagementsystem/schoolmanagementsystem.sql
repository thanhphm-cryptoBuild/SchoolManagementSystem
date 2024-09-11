-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th9 10, 2024 lúc 11:40 AM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `gogo`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `classes`
--

CREATE TABLE `classes` (
  `classID` int(11) NOT NULL,
  `className` varchar(100) DEFAULT NULL,
  `staffID` int(11) DEFAULT NULL COMMENT 'Khóa ngoại liên kết với bảng staff'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `classteacher`
--

CREATE TABLE `classteacher` (
  `classTeacherID` int(11) NOT NULL,
  `staffID` int(11) DEFAULT NULL,
  `classID` int(11) DEFAULT NULL,
  `assignedDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `extracurricularactivities`
--

CREATE TABLE `extracurricularactivities` (
  `activityID` int(11) NOT NULL,
  `studentID` int(11) DEFAULT NULL,
  `activityName` varchar(255) DEFAULT NULL,
  `participationDate` date DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  `achievement` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `feedback`
--

CREATE TABLE `feedback` (
  `feedbackID` int(11) NOT NULL,
  `studentID` int(11) DEFAULT NULL,
  `feedbackDate` date DEFAULT NULL,
  `feedbackContent` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `grade`
--

CREATE TABLE `grade` (
  `gradeID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `staffID` int(11) DEFAULT NULL,
  `subjectID` int(11) NOT NULL,
  `score` decimal(5,2) NOT NULL,
  `date` date DEFAULT NULL,
  `comment` text DEFAULT NULL COMMENT 'Ghi chú thêm về điểm'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `passwordchange`
--

CREATE TABLE `passwordchange` (
  `changeID` int(11) NOT NULL,
  `userID` int(11) DEFAULT NULL,
  `oldPassword` varchar(255) DEFAULT NULL,
  `newPassword` varchar(255) DEFAULT NULL,
  `changeDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `permissions`
--

CREATE TABLE `permissions` (
  `permissionID` int(11) NOT NULL,
  `permissionName` varchar(100) DEFAULT NULL,
  `permissionDescription` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `roleID` int(11) NOT NULL,
  `roleName` varchar(100) DEFAULT NULL,
  `roleDescription` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role_permissions`
--

CREATE TABLE `role_permissions` (
  `roleID` int(11) DEFAULT NULL,
  `permissionID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `staff`
--

CREATE TABLE `staff` (
  `staffID` int(11) NOT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `birthDate` date DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `hireDate` date DEFAULT NULL,
  `salary` decimal(10,2) DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `profilePicture` varchar(255) DEFAULT NULL,
  `sex` enum('male','female') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `staffeducation`
--

CREATE TABLE `staffeducation` (
  `staffID` int(11) DEFAULT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `graduationYear` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `staffexperience`
--

CREATE TABLE `staffexperience` (
  `staffID` int(11) DEFAULT NULL,
  `previousCompany` varchar(255) DEFAULT NULL,
  `jobTitle` varchar(255) DEFAULT NULL,
  `yearsOfExperience` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `stafffamilybackground`
--

CREATE TABLE `stafffamilybackground` (
  `staffID` int(11) DEFAULT NULL,
  `spouseName` varchar(255) DEFAULT NULL,
  `children` varchar(255) DEFAULT NULL,
  `emergencyContact` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `staffpromotion`
--

CREATE TABLE `staffpromotion` (
  `staffID` int(11) DEFAULT NULL,
  `promotionDate` date DEFAULT NULL,
  `newPosition` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `student`
--

CREATE TABLE `student` (
  `studentID` int(11) NOT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `birthDate` date DEFAULT NULL,
  `registrationNo` varchar(50) DEFAULT NULL,
  `batch` varchar(50) DEFAULT NULL,
  `admissionDate` date DEFAULT NULL,
  `previousInstitute` varchar(255) DEFAULT NULL,
  `reasonForLeaving` varchar(255) DEFAULT NULL,
  `guardianContact` varchar(20) DEFAULT NULL,
  `profilePicture` varchar(255) DEFAULT NULL,
  `classID` int(11) DEFAULT NULL,
  `sex` enum('male','female') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `subjects`
--

CREATE TABLE `subjects` (
  `subjectID` int(11) NOT NULL,
  `subjectName` varchar(100) DEFAULT NULL,
  `subjectDescription` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `timetable`
--

CREATE TABLE `timetable` (
  `timetableID` int(11) NOT NULL,
  `classID` int(11) DEFAULT NULL,
  `subjectID` int(11) DEFAULT NULL,
  `staffID` int(11) DEFAULT NULL,
  `dayOfWeek` varchar(20) DEFAULT NULL,
  `startTime` time DEFAULT NULL,
  `endTime` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tuitionfee`
--

CREATE TABLE `tuitionfee` (
  `tuitionID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_status` enum('paid','unpaid') NOT NULL,
  `payment_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `useractivitylog`
--

CREATE TABLE `useractivitylog` (
  `logID` int(11) NOT NULL,
  `userID` int(11) DEFAULT NULL,
  `activityType` varchar(100) DEFAULT NULL,
  `activityDate` datetime DEFAULT NULL,
  `activityDetails` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `userroles`
--

CREATE TABLE `userroles` (
  `userID` int(11) DEFAULT NULL,
  `roleID` int(11) DEFAULT NULL,
  `assignedDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `staffID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`classID`),
  ADD KEY `fk_staff` (`staffID`);

--
-- Chỉ mục cho bảng `classteacher`
--
ALTER TABLE `classteacher`
  ADD PRIMARY KEY (`classTeacherID`),
  ADD KEY `teacherID` (`staffID`),
  ADD KEY `classID` (`classID`);

--
-- Chỉ mục cho bảng `extracurricularactivities`
--
ALTER TABLE `extracurricularactivities`
  ADD PRIMARY KEY (`activityID`),
  ADD KEY `studentID` (`studentID`);

--
-- Chỉ mục cho bảng `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedbackID`),
  ADD KEY `studentID` (`studentID`);

--
-- Chỉ mục cho bảng `grade`
--
ALTER TABLE `grade`
  ADD PRIMARY KEY (`gradeID`),
  ADD KEY `studentID` (`studentID`),
  ADD KEY `staffID` (`staffID`),
  ADD KEY `subjectID` (`subjectID`);

--
-- Chỉ mục cho bảng `passwordchange`
--
ALTER TABLE `passwordchange`
  ADD PRIMARY KEY (`changeID`),
  ADD KEY `userID` (`userID`);

--
-- Chỉ mục cho bảng `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`permissionID`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`roleID`);

--
-- Chỉ mục cho bảng `role_permissions`
--
ALTER TABLE `role_permissions`
  ADD KEY `roleID` (`roleID`),
  ADD KEY `permissionID` (`permissionID`);

--
-- Chỉ mục cho bảng `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staffID`);

--
-- Chỉ mục cho bảng `staffeducation`
--
ALTER TABLE `staffeducation`
  ADD KEY `staffID` (`staffID`);

--
-- Chỉ mục cho bảng `staffexperience`
--
ALTER TABLE `staffexperience`
  ADD KEY `staffID` (`staffID`);

--
-- Chỉ mục cho bảng `stafffamilybackground`
--
ALTER TABLE `stafffamilybackground`
  ADD KEY `staffID` (`staffID`);

--
-- Chỉ mục cho bảng `staffpromotion`
--
ALTER TABLE `staffpromotion`
  ADD KEY `staffID` (`staffID`);

--
-- Chỉ mục cho bảng `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`studentID`),
  ADD UNIQUE KEY `registrationNo` (`registrationNo`),
  ADD KEY `fk_classID_student` (`classID`);

--
-- Chỉ mục cho bảng `subjects`
--
ALTER TABLE `subjects`
  ADD PRIMARY KEY (`subjectID`);

--
-- Chỉ mục cho bảng `timetable`
--
ALTER TABLE `timetable`
  ADD PRIMARY KEY (`timetableID`),
  ADD KEY `classID` (`classID`),
  ADD KEY `subjectID` (`subjectID`),
  ADD KEY `teacherID` (`staffID`);

--
-- Chỉ mục cho bảng `tuitionfee`
--
ALTER TABLE `tuitionfee`
  ADD PRIMARY KEY (`tuitionID`),
  ADD KEY `studentID` (`studentID`);

--
-- Chỉ mục cho bảng `useractivitylog`
--
ALTER TABLE `useractivitylog`
  ADD PRIMARY KEY (`logID`),
  ADD KEY `userID` (`userID`);

--
-- Chỉ mục cho bảng `userroles`
--
ALTER TABLE `userroles`
  ADD KEY `userID` (`userID`),
  ADD KEY `roleID` (`roleID`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `staffID` (`staffID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `classes`
--
ALTER TABLE `classes`
  MODIFY `classID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `classteacher`
--
ALTER TABLE `classteacher`
  MODIFY `classTeacherID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `extracurricularactivities`
--
ALTER TABLE `extracurricularactivities`
  MODIFY `activityID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `feedback`
--
ALTER TABLE `feedback`
  MODIFY `feedbackID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `grade`
--
ALTER TABLE `grade`
  MODIFY `gradeID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `passwordchange`
--
ALTER TABLE `passwordchange`
  MODIFY `changeID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `permissions`
--
ALTER TABLE `permissions`
  MODIFY `permissionID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `roleID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `staff`
--
ALTER TABLE `staff`
  MODIFY `staffID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `student`
--
ALTER TABLE `student`
  MODIFY `studentID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `subjects`
--
ALTER TABLE `subjects`
  MODIFY `subjectID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `timetable`
--
ALTER TABLE `timetable`
  MODIFY `timetableID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tuitionfee`
--
ALTER TABLE `tuitionfee`
  MODIFY `tuitionID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `useractivitylog`
--
ALTER TABLE `useractivitylog`
  MODIFY `logID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `classes`
--
ALTER TABLE `classes`
  ADD CONSTRAINT `classes_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`),
  ADD CONSTRAINT `fk_staff` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`);

--
-- Các ràng buộc cho bảng `classteacher`
--
ALTER TABLE `classteacher`
  ADD CONSTRAINT `classteacher_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`),
  ADD CONSTRAINT `classteacher_ibfk_2` FOREIGN KEY (`classID`) REFERENCES `classes` (`classID`);

--
-- Các ràng buộc cho bảng `extracurricularactivities`
--
ALTER TABLE `extracurricularactivities`
  ADD CONSTRAINT `extracurricularactivities_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`);

--
-- Các ràng buộc cho bảng `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`);

--
-- Các ràng buộc cho bảng `grade`
--
ALTER TABLE `grade`
  ADD CONSTRAINT `grade_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`),
  ADD CONSTRAINT `grade_ibfk_2` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`),
  ADD CONSTRAINT `grade_ibfk_3` FOREIGN KEY (`subjectID`) REFERENCES `subjects` (`subjectID`);

--
-- Các ràng buộc cho bảng `passwordchange`
--
ALTER TABLE `passwordchange`
  ADD CONSTRAINT `passwordchange_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`);

--
-- Các ràng buộc cho bảng `role_permissions`
--
ALTER TABLE `role_permissions`
  ADD CONSTRAINT `role_permissions_ibfk_1` FOREIGN KEY (`roleID`) REFERENCES `roles` (`roleID`),
  ADD CONSTRAINT `role_permissions_ibfk_2` FOREIGN KEY (`permissionID`) REFERENCES `permissions` (`permissionID`);

--
-- Các ràng buộc cho bảng `staffeducation`
--
ALTER TABLE `staffeducation`
  ADD CONSTRAINT `staffeducation_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`);

--
-- Các ràng buộc cho bảng `staffexperience`
--
ALTER TABLE `staffexperience`
  ADD CONSTRAINT `staffexperience_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`);

--
-- Các ràng buộc cho bảng `stafffamilybackground`
--
ALTER TABLE `stafffamilybackground`
  ADD CONSTRAINT `stafffamilybackground_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`);

--
-- Các ràng buộc cho bảng `staffpromotion`
--
ALTER TABLE `staffpromotion`
  ADD CONSTRAINT `staffpromotion_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`);

--
-- Các ràng buộc cho bảng `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_classID_student` FOREIGN KEY (`classID`) REFERENCES `classes` (`classID`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `timetable`
--
ALTER TABLE `timetable`
  ADD CONSTRAINT `timetable_ibfk_1` FOREIGN KEY (`classID`) REFERENCES `classes` (`classID`),
  ADD CONSTRAINT `timetable_ibfk_2` FOREIGN KEY (`subjectID`) REFERENCES `subjects` (`subjectID`),
  ADD CONSTRAINT `timetable_ibfk_3` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`);

--
-- Các ràng buộc cho bảng `tuitionfee`
--
ALTER TABLE `tuitionfee`
  ADD CONSTRAINT `tuitionfee_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`);

--
-- Các ràng buộc cho bảng `useractivitylog`
--
ALTER TABLE `useractivitylog`
  ADD CONSTRAINT `useractivitylog_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`);

--
-- Các ràng buộc cho bảng `userroles`
--
ALTER TABLE `userroles`
  ADD CONSTRAINT `userroles_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`),
  ADD CONSTRAINT `userroles_ibfk_2` FOREIGN KEY (`roleID`) REFERENCES `roles` (`roleID`);

--
-- Các ràng buộc cho bảng `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
