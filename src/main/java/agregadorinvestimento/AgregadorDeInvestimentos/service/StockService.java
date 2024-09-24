package agregadorinvestimento.AgregadorDeInvestimentos.service;

import agregadorinvestimento.AgregadorDeInvestimentos.controller.dto.CreateStockDto;
import agregadorinvestimento.AgregadorDeInvestimentos.entity.Stock;
import agregadorinvestimento.AgregadorDeInvestimentos.repository.StockRepository;
import org.springframework.stereotype.Service;


@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {

        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);
    }
}
