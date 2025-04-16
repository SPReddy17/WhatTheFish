pipeline {
    agent any

    environment {
        GITHUB_TOKEN = credentials('wtf-token')
        GITHUB_REPO = 'https://github.com/SPReddy17/WhatTheFish'
    }

    triggers {
        cron('H/5 * * * *')
    }

    stages {
        stage ('Check and Close PR'){
            steps {
                    script {
                        import java.text.SimpleDateFormat
                        def staleMinutes = 5 // Define the number of days to consider a PR stale
                        def now = new Date()
                        // def staleDate = now - staleDays

                        // Fetch PRs using GitHub API
                        def response = httpRequest(
                                url: "https://api.github.com/repos/SPReddy17/WhatTheFish/pulls",
                                customHeaders: [[name: 'Authorization', value: "Bearer ${env.GITHUB_TOKEN}"]],
                                validResponseCodes: '200'
                        )
                        def prs = []
                        if (response.content?.trim()) {
                            prs = readJSON text: response.content
                        } else {
                            error "Response content is empty or null"
                        }

                        def parseGithubDate(String timestamp) {
                            def format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                            format.setTimeZone(TimeZone.getTimeZone('UTC'))
                            return format.parse(timestamp)
                        }

                        prs.each { pr ->
                            def updatedAt = parseGithubDate(pr.updated_at)
                            def diffInMinutes = (now.time - updated_at.time)/(1000*60)
                            if (diffInMinutes > staleMinutes) {
                                // Close the stale PR
                                httpRequest(
                                        httpMode: 'PATCH',
                                        url: "${env.GITHUB_REPO}/pulls/${pr.number}",
                                        customHeaders: [[name: 'Authorization', value: "Bearer ${env.GITHUB_TOKEN}"]],
                                        requestBody: '{"state": "closed"}',
                                        validResponseCodes: '200'
                                )
                            echo "Closed stale PR #${pr.number} (stale for ${diffInMinutes} minutes)"
                            }
                        }
                    }
            }
       }
    }
}