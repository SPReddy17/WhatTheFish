pipeline {
    agent any

    environment {
        GITHUB_TOKEN = credentials('wtf-token')
        GITHUB_REPO = 'https://api.github.com/repos/SPReddy17/WhatTheFish'
    }

    triggers {
        cron('H/5 * * * *')
    }

    stages {
        stage('Check and Close PR') {
            steps {
                script {



                    // Define the date format for GitHub timestamps
                    def parseGithubDate = { String timestamp ->
                        def format = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        format.setTimeZone(java.util.TimeZone.getTimeZone("UTC"))
                        return format.parse(timestamp)
                    }

                    def staleMinutes = 5 // Define the number of minutes to consider a PR stale
                    def now = new Date()

                    // Fetch PRs using GitHub API
                    def response = httpRequest(
                        url: "${env.GITHUB_REPO}/pulls",
                        customHeaders: [[name: 'Authorization', value: "Bearer ${env.GITHUB_TOKEN}"]],
                        validResponseCodes: '200'
                    )

                    def prs = []
                    if (response.content?.trim()) {
                        prs = readJSON text: response.content
                    } else {
                        error "Response content is empty or null"
                    }

                    prs.each { pr ->
                        try {
                            // Parse the 'updated_at' timestamp and calculate the time difference
                            def updatedAt = parseGithubDate(pr.updated_at)
                            def diffInMinutes = (now.time - updatedAt.time) / (1000 * 60)

                            if (diffInMinutes > staleMinutes) {
                                // Close the stale PR
                                def closeResponse = httpRequest(
                                    httpMode: 'PATCH',
                                    url: "${env.GITHUB_REPO}/pulls/${pr.number}",
                                    customHeaders: [[name: 'Authorization', value: "Bearer ${env.GITHUB_TOKEN}"]],
                                    requestBody: '{"state": "closed"}',
                                    validResponseCodes: '200'
                                )
                                echo "Closed stale PR #${pr.number} (stale for ${diffInMinutes} minutes)"
                            }
                        } catch (Exception e) {
                            echo "Failed to handle PR #${pr.number}: ${e}"
                        }
                    }
                }
            }
        }
    }
}
