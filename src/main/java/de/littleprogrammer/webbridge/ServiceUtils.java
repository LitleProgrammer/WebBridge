package de.littleprogrammer.webbridge;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceConfiguration;
import de.dytanic.cloudnet.driver.service.ServiceId;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.driver.service.ServiceTask;

public class ServiceUtils {

    private CloudNetDriver driver = CloudNetDriver.getInstance();

    public void stopService(String serviceName) {
        if (driver.getCloudServiceProvider().getCloudServiceByName(serviceName) == null) return;
        ServiceId id = driver.getCloudServiceProvider().getCloudServiceByName(serviceName).getServiceId();
        driver.getCloudServiceProvider(id.getUniqueId()).stop();
    }

    public void startService(String serviceName) {
        if (driver.getCloudServiceProvider().getCloudServiceByName(serviceName) == null) return;
        ServiceId id = driver.getCloudServiceProvider().getCloudServiceByName(serviceName).getServiceId();
        driver.getCloudServiceProvider(id.getUniqueId()).start();
    }

    public void restartService(String serviceName) {
        if (driver.getCloudServiceProvider().getCloudServiceByName(serviceName) == null) return;
        ServiceId id = driver.getCloudServiceProvider().getCloudServiceByName(serviceName).getServiceId();
        driver.getCloudServiceProvider(id.getUniqueId()).restart();
    }

    public void addService(String taskName) {
        if (driver.getServiceTaskProvider().isServiceTaskPresent(taskName)) {
            ServiceTask task = driver.getServiceTaskProvider().getServiceTask(taskName);
            ServiceInfoSnapshot serviceInfoSnapshot = ServiceConfiguration.builder(task).build().createNewService();

            if (serviceInfoSnapshot != null) {
                serviceInfoSnapshot.provider().start();
            }
        }
    }

    public void kickService(String serviceName) {
        if (driver.getCloudServiceProvider().getCloudServiceByName(serviceName) == null) return;
        ServiceId id = driver.getCloudServiceProvider().getCloudServiceByName(serviceName).getServiceId();
        driver.getCloudServiceProvider(id.getUniqueId()).runCommand("kick @a");
    }

}
