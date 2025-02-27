package org.interview.vehicleregistration.controller;

import lombok.RequiredArgsConstructor;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.service.StatisticsServiceImpl;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

    private final StatisticsServiceImpl statisticsService;

    @GetMapping(path = "/{accountId}")
    public ApiResponse<?> getStatisticsByAccountId(@PathVariable String accountId) {
        return statisticsService.getStatisticsByAccountId(accountId);
    }

    @GetMapping
    public ApiResponse<?> getAllStatistics(
                                           @RequestParam(name = "page",
                                                   defaultValue = "0", required = false) int page,
                                           @RequestParam(name = "size",
                                                   defaultValue = "5", required = false) int size) {
        return statisticsService.getStatisticsByAccountId(page, size);
    }
}

//Server odgovara sa JSON objektom, odnosno
//mapom ključ:vrijednost, gdje je ključ
//
//Page 3 of 4
//accountId, a vrijednost broj automobila koje je
//registrovao taj korisnik.
//
////statistics/accountID