try {
stage("poddd") {
 podTemplate(label: 'mypod' ,containers: [
        containerTemplate(image: 'docker', name: 'docker', command: 'cat', ttyEnabled: true),
        containerTemplate(name: 'kubectl', image: 'harbor.picsart.tools/analytics/kubectl:latest', ttyEnabled: true, command: 'cat')],
        imagePullSecrets: [ 'harbor' ],    
        volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')] ) {
    node('mypod') {  
        container('kubectl') {
        dir('.') {
            stage("check deployment") {  
                       
                       sh "kubectl get deployments"
                       def folderName="test_folder/test_job"
                       //def jenkinsJobs = Jenkins.instance.getAllItems(Job.class).findAll  { job -> job.isDisabled() }
                       def matchedJobs = Jenkins.instance.getItemByFullName(folderName).allJobs.findAll  { job -> job.isDisabled() }
                       //println matchedJobs.name
                       //.each {job -> println("Disabled job:" + job) }                       
                       //def disabledjobs = matchedJobs.each { //job ->  println job.name 
                       //job.delete() 
                       //}
                       //println disabledjobs.name[0]
                       //println disabledjobs.name[1]
                       sh "echo 444"
                       stage('disable deployment') {
                       for (int i = 0; i < 2 ; i++) {
                            //def disabledjob = disabledjobs[j];
                            stage("disable job") {
                                sh "echo $i"
                                //println disabledjobs[i]
                            }
                        }
                    
                           
                       }    
                    } 
                }    
            }
        }
    }
  
}

} catch (err) {
            echo "FAILING.... ${err}"
        }
