package com.sicau.devicemanagement.service;


import com.sicau.devicemanagement.domain.Device;
import com.sicau.devicemanagement.domain.model.DeviceUsingSituation;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface IDeviceService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Device selectDeviceById(String id);

    /**
     * 查询【设备】列表  通过金额
     * 
     * @param device 【设备】
     * @return 【设备】集合
     */
    public List<Device> selectDeviceList(Device device);

    /**
     * 新增【请填写功能名称】
     * 
     * @param device 【请填写功能名称】
     * @return 结果
     */
    public int insertDevice(Device device);

    /**
     * 修改【请填写功能名称】
     * 
     * @param device 【请填写功能名称】
     * @return 结果
     */
    public int updateDevice(Device device);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteDeviceByIds(String[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDeviceById(String id);

    /**
     * 查询设备总状态
     *
     * @return {@link DeviceUsingSituation }
     * @author sora
     * @date 2022/01/18
     */
    public DeviceUsingSituation queryDeviceTotalStatus();

    /**
     * 是否是设备所有者
     *
     * @param uid uid
     * @param id  id
     * @return boolean
     * @author sora
     * @date 2022/01/19
     */
    boolean isDeviceOwner(String uid, String id);

    /**
     * 更换设备
     *
     * @param uid uid
     * @param id  id
     * @author sora
     * @date 2022/01/19
     */
    Device replaceDevice(String uid, String id);

    /**
     * 更新设备状态
     *
     * @param id     id
     * @param status 状态
     * @author sora
     * @date 2022/01/27
     */
    void updateDeviceStatus(String id, String status);

    /**
     * 确认设备损坏
     *
     * @param id id
     * @author sora
     * @date 2022/01/27
     */
    void deviceBroken(String id);

    /**
     * 添加设备
     *
     * @param device         设备
     * @author sora
     * @date 2022/02/08
     */
    int addDevice(Device device);

    int addDevice(List<Device> list);
}
