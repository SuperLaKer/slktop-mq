package slktop.mq.order.webmvc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slktop.mq.order.webmvc.entity.vo.ToSecKillProductVo;
import slktop.mq.order.webmvc.service.IProductService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author slk
 * @since 2021-08-02
 */
@RestController
@RequestMapping("/web/product")
public class ProductController {

    @Autowired
    IProductService productService;

    /**
     * 秒杀商品上架
     * @param toSecKillProductVo 商品模型
     * @return 是否上架成功
     */
    @PostMapping("/seckill")
    public String secKill(@RequestBody @Validated ToSecKillProductVo toSecKillProductVo){
        return productService.createSecKillProduct(toSecKillProductVo);
    }

}
