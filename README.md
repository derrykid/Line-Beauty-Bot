# Line-Beauty-Bot

![Java](https://img.shields.io/badge/Java-17-ff696c)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=derrykid_Line-Beauty-Bot&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=derrykid_Line-Beauty-Bot)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=derrykid_Line-Beauty-Bot&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=derrykid_Line-Beauty-Bot) 
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=derrykid_Line-Beauty-Bot&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=derrykid_Line-Beauty-Bot)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=derrykid_Line-Beauty-Bot&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=derrykid_Line-Beauty-Bot)

## what this bot does:
This Line bot goes to Ptt beauty and get the post from yesterday that receives most likes. It works on schedule.

When in chat group, use '/subscribe' command to register the service. This bot run on a 24-hour fixed-delay.

It means when you subscribe at 8 a.m. in the morning, it will send you the pictures right away, and register the routine.
After 24 hours, the next day, at 8 a.m., it will automatically send you pictures again (of different post).

## Line Bot config
You can find the config file at `src/main/resources/application.yml`. Please replace it with your own channel-token and channel-secret

You can deploy your bot at Heroku.

If you don't know how to do this, please follow the instruction provides here: [How to deploy at heroku](https://github.com/line/line-bot-sdk-java/tree/master/sample-spring-boot-echo) 

This guide shows you how to deploy a sample-echo bot. Follow the steps.

---

### Bot QR code
![QR-Code](./qr-code.png) 
