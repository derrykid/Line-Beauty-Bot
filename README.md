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

