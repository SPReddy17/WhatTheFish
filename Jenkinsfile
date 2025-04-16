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
                        def staleMinutes = 5 // Define the number of days to consider a PR stale
                        def now = new Date()
                        // def staleDate = now - staleDays

                        // Fetch PRs using GitHub API
                        def response = httpRequest(
                                url: "${env.GITHUB_REPO}/pulls",
                                customHeaders: [[name: 'Authorization', value: "Bearer ${env.GITHUB_TOKEN}"]],
                                validResponseCodes: '200'
                        )

                        if (response.content?.trim()) {
                            def prs = readJSON text: response.content
                        } else {
                            error "Response content is empty or null"
                        }


                        prs.each { pr ->
                            def updatedAt = Date.parse ("yyyy-MM-dd'T'HH: mm: ss 'Z'", pr.updated_at)
                            def diffInMinutes = (now.time - updated_at.time)/(1000*60)
                            if (diffInMinutes > staleMinutes) {
                                // Close the stale PR
                                httpRequest(
                                        httpMode: 'PATCH',
                                        url: "https://api.github.com/repos/${env.GITHUB_REP0}/pulls/${pr.number}",
                                        customHeaders: [[name: 'Authorization', value: "Bearer ${env.GITHUB_TOKEN}"]],
                                        requestBody: '("state": "closed"}',
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