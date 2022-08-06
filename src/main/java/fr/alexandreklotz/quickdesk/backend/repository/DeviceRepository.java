package fr.alexandreklotz.quickdesk.backend.repository;

import fr.alexandreklotz.quickdesk.backend.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

    @Query("FROM Device d WHERE d.deviceName = :name")
    Optional<Device> findDeviceByName(@Param("name")String name);

    @Query("FROM Device d WHERE d.deviceSerialNbr = :serialNbr")
    Optional<Device> findDeviceBySerialNumber(@Param("serialNbr")String serialNbr);

    @Query("FROM Device d WHERE d.deviceManufacturer = :manufacturer")
    List<Device> findDeviceByManufacturer (@Param("manufacturer")String manufacturer);

}
