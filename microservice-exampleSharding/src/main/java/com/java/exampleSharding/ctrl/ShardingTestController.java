package com.java.exampleSharding.ctrl;

import com.java.dto.ResultMsg;
import com.java.exampleSharding.entity.mybatis.GoodsInfoQuery;
import com.java.exampleSharding.service.IShardingTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(description = "分片测试接口")
@RestController
@RequestMapping("/shardingTest")
public class ShardingTestController {
    private static final Logger logger = LoggerFactory.getLogger(ShardingTestController.class);
    
    @Resource
    private IShardingTestService shardingTestService;
    
    @ApiOperation(value = "分片添加数据", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/add")
    public ResultMsg add(HttpServletRequest request, HttpServletResponse response) {
        return this.shardingTestService.add();
    }
    
    @ApiOperation(value = "根据分片键查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "query")})
    @GetMapping(value = "/queryGoodsById")
    public List<GoodsInfoQuery> queryGoodsById(@RequestParam(value = "goodsId", required = true) long goodsId) {
        return this.shardingTestService.queryGoodsById(goodsId);
    }
    
    @ApiOperation(value = "不根据分片键查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "goodsName", value = "名称", required = true, paramType = "query")})
    @GetMapping(value = "/queryGoodsByName")
    public List<GoodsInfoQuery> queryGoodsByName(@RequestParam(value = "goodsName", required = true) String goodsName) {
        return this.shardingTestService.queryGoodsByName(goodsName);
    }
    
    @ApiOperation(value = "不根据分片键，分页查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/queryGoodsForPage")
    public List<GoodsInfoQuery> queryGoodsForPage() {
        return this.shardingTestService.queryGoodsForPage();
    }
    
    @ApiOperation(value = "不根据分片键，分页查询，排序", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/queryGoodsOrderByForPage")
    public List<GoodsInfoQuery> queryGoodsOrderByForPage() {
        return this.shardingTestService.queryGoodsOrderByForPage();
    }
    
    @ApiOperation(value = "测试弱XA事务", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/transcationTest")
    public ResultMsg transcationTest() throws Exception {
        return this.shardingTestService.transcationTest();
    }
}
