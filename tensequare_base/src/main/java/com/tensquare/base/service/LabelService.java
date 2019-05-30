package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lhh
 * @date 2019/5/31 0:32
 */
/**
 * 标签的service
 */
@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 根据id查询
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 添加
     */
    public void add(Label label){
        //设置id
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /**
     * 修改
     */
    public void update(Label label){
        labelDao.save(label);
    }


    /**
     * 根据id删除
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    /**
     * 创建Specification对象
     */
    private Specification<Label> createSpecification(Map searchMap){
        //通常提供Specification接口的匿名实现类对象
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1.准备List集合，存储Predicate条件对象
                List<Predicate> preList = new ArrayList<Predicate>();

                //2.根据用户输入的条件，构建Predicate条件，把条件放入List集合（***）
                if (searchMap.get("labelname") != null && !(searchMap.get("labelname")).equals("")) {
                    preList.add(criteriaBuilder.like(root.get("labelname").as(String.class), "%" + searchMap.get("labelname") + "%"));
                }

                if (searchMap.get("state") != null && !(searchMap.get("state")).equals("")) {
                    preList.add(criteriaBuilder.equal(root.get("state").as(String.class), searchMap.get("state")));
                }

                if (searchMap.get("recommend") != null && !(searchMap.get("recommend")).equals("")) {
                    preList.add(criteriaBuilder.equal(root.get("recommend").as(String.class), searchMap.get("recommend")));
                }

                //3.使用连接条件把条件对象进行连接: Labelname like '%Java%' and state = '1' and count = 10

                //preList.toArray(preArray): 从preList集合中取出每个元素，逐个放入preArray数组里面，返回preArray数组
                Predicate[] preArray = new Predicate[preList.size()];
                return criteriaBuilder.and(preList.toArray(preArray));
            }
        };
    }

    /**
     * 条件查询
     */
    public List<Label> findSearch(Map searchMap){
        //创建Specification对象
        Specification<Label> spec = createSpecification(searchMap);
        return labelDao.findAll(spec);
    }

    /**
     * 带条件的分页查询
     */
    public Page<Label> findSearch(Map searchMap, int page, int size){
        //创建Specification对象
        Specification<Label> spec = createSpecification(searchMap);
        //注意：PageRequest的page参数从0开始计算
        return labelDao.findAll(spec, PageRequest.of(page-1,size));
    }
}
