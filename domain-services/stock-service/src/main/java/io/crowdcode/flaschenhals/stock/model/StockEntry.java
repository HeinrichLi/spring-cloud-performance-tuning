package io.crowdcode.flaschenhals.stock.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "version"})
@ToString(exclude = "stock")
@Table(indexes = @Index(name = "stock_product_quantity_added", columnList = "stock_id,productid,quantity,addedat"))
public class StockEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @NotNull
    private Long productId;

    @NotNull
    private Long quantity;

    @NotNull
    private BigDecimal price;

    @NotNull
    private LocalDateTime addedAt;

    @ManyToOne
    private Stock stock;
}
