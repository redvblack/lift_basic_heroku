# lift_basic_heroku
The lift basic project with extra files needed to deploy to heroku

check it out, and assuming you have the heroku toolbelt you can heroku create, git push heroku master to deploy to heroku

if your lift run.mode is set to production then it'll try to use heroku postgres rather than H2. heroku config:add run.mode=production 

and make sure that there's a postgres DB sat there waiting for it to talk to.
