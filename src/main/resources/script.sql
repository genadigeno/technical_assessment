USE [master]
GO

/****** Object:  Database [technical_assessment]    Script Date: 11/21/2021 13:11:49 ******/
CREATE DATABASE [technical_assessment]
 CONTAINMENT = NONE
 ON  PRIMARY
( NAME = N'technical_assessment', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\technical_assessment.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON
( NAME = N'technical_assessment_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\technical_assessment_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [technical_assessment].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [technical_assessment] SET ANSI_NULL_DEFAULT OFF
GO

ALTER DATABASE [technical_assessment] SET ANSI_NULLS OFF
GO

ALTER DATABASE [technical_assessment] SET ANSI_PADDING OFF
GO

ALTER DATABASE [technical_assessment] SET ANSI_WARNINGS OFF
GO

ALTER DATABASE [technical_assessment] SET ARITHABORT OFF
GO

ALTER DATABASE [technical_assessment] SET AUTO_CLOSE OFF
GO

ALTER DATABASE [technical_assessment] SET AUTO_SHRINK OFF
GO

ALTER DATABASE [technical_assessment] SET AUTO_UPDATE_STATISTICS ON
GO

ALTER DATABASE [technical_assessment] SET CURSOR_CLOSE_ON_COMMIT OFF
GO

ALTER DATABASE [technical_assessment] SET CURSOR_DEFAULT  GLOBAL
GO

ALTER DATABASE [technical_assessment] SET CONCAT_NULL_YIELDS_NULL OFF
GO

ALTER DATABASE [technical_assessment] SET NUMERIC_ROUNDABORT OFF
GO

ALTER DATABASE [technical_assessment] SET QUOTED_IDENTIFIER OFF
GO

ALTER DATABASE [technical_assessment] SET RECURSIVE_TRIGGERS OFF
GO

ALTER DATABASE [technical_assessment] SET  DISABLE_BROKER
GO

ALTER DATABASE [technical_assessment] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO

ALTER DATABASE [technical_assessment] SET DATE_CORRELATION_OPTIMIZATION OFF
GO

ALTER DATABASE [technical_assessment] SET TRUSTWORTHY OFF
GO

ALTER DATABASE [technical_assessment] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO

ALTER DATABASE [technical_assessment] SET PARAMETERIZATION SIMPLE
GO

ALTER DATABASE [technical_assessment] SET READ_COMMITTED_SNAPSHOT OFF
GO

ALTER DATABASE [technical_assessment] SET HONOR_BROKER_PRIORITY OFF
GO

ALTER DATABASE [technical_assessment] SET RECOVERY SIMPLE
GO

ALTER DATABASE [technical_assessment] SET  MULTI_USER
GO

ALTER DATABASE [technical_assessment] SET PAGE_VERIFY CHECKSUM
GO

ALTER DATABASE [technical_assessment] SET DB_CHAINING OFF
GO

ALTER DATABASE [technical_assessment] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO

ALTER DATABASE [technical_assessment] SET TARGET_RECOVERY_TIME = 0 SECONDS
GO

ALTER DATABASE [technical_assessment] SET  READ_WRITE
GO
---------------------------------------------------- CREATE TABLE ------------------------------------------------------
USE [technical_assessment]
GO
/****** Object:  Table [dbo].[tasks]    Script Date: 11/21/2021 13:11:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tasks](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](256) NULL,
	[status] [int] NOT NULL,
	[result] [nvarchar](256) NULL,
	[message] [varchar](256) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[tasks] ADD  CONSTRAINT [DF__tasks__status__164452B1]  DEFAULT ((0)) FOR [status]
GO
