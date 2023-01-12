package com.nfteam.server.coin;

import com.nfteam.server.item.entity.ItemPrice;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Entity
@Table(name = "coin")
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_id")
    private Long coinId;

    @Column(name = "coin_name", nullable = false, unique = true, length = 100)
    private String coinName;

    @Column(name = "withdrwal_fee", length = 400)
    private double withdrawlFee;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "coin")
    private List<ItemPrice> itemPrices = new ArrayList<>();

    protected Coin() {
    }

    @Builder
    public Coin(String coinName, double withdrawlFee) {
        this.coinName = coinName;
        this.withdrawlFee = withdrawlFee;
    }

    public void changeCoinName(final String coinName) {
        this.coinName = coinName;
    }

    public void changeWithdrawlFee(final double withdrawlFee) {
        this.withdrawlFee = withdrawlFee;
    }
}
