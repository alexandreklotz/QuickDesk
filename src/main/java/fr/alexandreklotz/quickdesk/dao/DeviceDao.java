package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceDao extends JpaRepository<Device, Integer> {

}