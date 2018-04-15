#!/usr/bin/groovy
def checkDiff() {
    def diff = 0

    sh (returnStdout: true, script: "git diff | wc -l ").trim()
    if ( diff != 0 ) {
        return true
    } else {
        return false
    }
}

def call(body) {
    def parameters = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = parameters
    body()

    def status = 1 
    def output = ''

    if (parameters.action=='push') {
        sshagent (credentials: ['f1fe8468-e322-4b55-8599-0a3a6b79acbb']) {
            if (checkDiff()) {
                sh("git config --global user.email ${parameters?.email}")
                sh("git config --global user.name ${parameters?.user}")
                sh("git checkout -b auto-${BUILD_NUMBER} ")
                sh("git add .")
                sh("git commit -m 'auto-commit-${parameters?.commitMessage}'")
                sh("git push origin auto-${BUILD_NUMBER}")                
            }
        }        
    }

    if (parameters.action=='pull') {
        sshagent (credentials: ['f1fe8468-e322-4b55-8599-0a3a6b79acbb']) {
            sh("git checkout -b ${parameters.branch} ")
            sh("git pull")
        }
    }

}