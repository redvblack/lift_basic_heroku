# lift_basic_heroku
The lift basic project with extra files needed to deploy to heroku

check it out, and assuming you have the heroku toolbelt you change directory to the project directory

heroku create
heroku config:add run.mode=production

Log into your heroku dashboard, and add the free postgres addon (you can add the logger addons and stuff too, they can be handy, but the postgres one is all that's required)

to deploy to heroku:

git push heroku master 

You can now access the app on heroku.

to redeploy, commit your changes to local git repo, (git commit) then git push heroku master to put it up.

Runs locally with an H2 database.
