package com.sicau.devicemanagement.service.impl;

import java.io.IOException;
import java.util.List;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.utils.file.FileUploadUtils;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Device;
import com.sicau.devicemanagement.domain.DeviceImg;
import com.sicau.devicemanagement.domain.DeviceType;
import com.sicau.devicemanagement.mapper.DeviceImgMapper;
import com.sicau.devicemanagement.mapper.DeviceTypeMapper;
import com.sicau.devicemanagement.service.IDeviceTypeService;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Service
public class DeviceTypeServiceImpl implements IDeviceTypeService
{
    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Autowired
    private DeviceImgMapper deviceImgMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public DeviceType selectDeviceTypeById(String id)
    {
        return deviceTypeMapper.selectDeviceTypeById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param deviceType 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DeviceType> selectDeviceTypeList(DeviceType deviceType)
    {
        return deviceTypeMapper.selectDeviceTypeList(deviceType);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param deviceType 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDeviceType(DeviceType deviceType)
    {
        return deviceTypeMapper.insertDeviceType(deviceType);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param deviceType 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDeviceType(DeviceType deviceType)
    {
        return deviceTypeMapper.updateDeviceType(deviceType);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDeviceTypeByIds(String[] ids)
    {
        return deviceTypeMapper.deleteDeviceTypeByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDeviceTypeById(String id)
    {
        return deviceTypeMapper.deleteDeviceTypeById(id);
    }

    /**
     * 添加设备类型
     *
     * @param deviceType 设备类型
     * @param files      文件
     * @return {@link int[] }
     * @author sora
     * @date 2022/02/09
     */
    @Override
    public int[] addDeviceType(DeviceType deviceType, MultipartFile[] files) {
        // 生成uuid
        String uuid = IdUtils.simpleUUID();
        deviceType.setId(uuid);
        // TODO: 2022/2/13 判断逻辑外键
        int count = deviceTypeMapper.insertDeviceType(deviceType);
        deviceType.setInventory(0);
        deviceType.setTotal(0);
        if (count < 1) {
            return null;
        }
        // 上传图片
        DeviceImg img = new DeviceImg();
        int flag = 0;
        if (files != null && files.length != 0) {
            for (MultipartFile file : files) {
                String filename = null;
                try {
                    filename = FileUploadUtils.upload(Constants.getDefaultUploadAddr(), file, new String[]{"jpg", "jpeg", "png", "bmp"});
                } catch (FileSizeLimitExceededException | IOException e) {
                    e.printStackTrace();
                }
                img.setDeviceTypeId(uuid);
                img.setUrl(filename);
                if (deviceImgMapper.insertDeviceImg(img) < 1) {
                    flag++;
                }
            }
        }
        return new int[]{count, flag};
    }
}
