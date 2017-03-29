package io.crowdcode.flaschenlager.stock.controller;

import io.crowdcode.flaschenlager.stock.model.Stock;
import io.crowdcode.flaschenlager.stock.model.StockEntryQuantity;
import io.crowdcode.flaschenlager.stock.model.StockEntryResponse;
import io.crowdcode.flaschenlager.stock.resources.EntryPullRequest;
import io.crowdcode.flaschenlager.stock.resources.EntryReceiptRequest;
import io.crowdcode.flaschenlager.stock.resources.StockRegisterRequest;
import io.crowdcode.flaschenlager.stock.service.StockService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @ApiOperation("Get all stocks")
    @GetMapping
    public List<Stock> getStocks() {
        return stockService.findAllStocks();
    }

    @ApiOperation("Create new stock")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createStock(@RequestBody @Valid StockRegisterRequest stockRegisterRequest) {
        stockService.registerStock(stockRegisterRequest.getName());
    }

    @ApiOperation("Get stock")
    @GetMapping(path = "/{stockId}")
    public Stock getStock(@PathVariable("stockId") Long stockId) {
        return stockService.findStock(stockId);
    }

    @ApiOperation("Get available product quantities")
    @GetMapping(path = "/{stockId}/entries")
    public List<StockEntryQuantity> getAvailableProductQuantities(@PathVariable("stockId") Long stockId) {
        return stockService.quantityOfAvailableProducts(stockId);
    }

    @ApiOperation("Receipt products")
    @PutMapping(path = "/{stockId}/entries")
    public void putToStock(@PathVariable("stockId") Long stockId, @RequestBody @Valid EntryReceiptRequest entry) {
        stockService.put(stockId, entry.getProductId(), entry.getQuantity(), entry.getPrice().doubleValue());
    }

    @ApiOperation("Pull product from stock.")
    @PostMapping(path = "/{stockId}/entries")
    public List<StockEntryResponse> getPull(@PathVariable("stockId") Long stockId,
                                            @RequestBody @Valid EntryPullRequest entryPullRequest) {
        return stockService.pull(stockId, entryPullRequest.getProductId(), entryPullRequest.getQuantity());
    }


}
