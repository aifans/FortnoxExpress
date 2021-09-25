package se.fortnox.codetest.fortnoxexpress.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@ApiModel(value="OrderAddDTO",description="the order data which web send to api when place an order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddDTO {

    @ApiModelProperty(value = "receiver name", required = true, example = "John")
    private String nameRecv;
    @ApiModelProperty(value = "box weight", required = true, example = "2")
    private BigDecimal weightBox;
    @ApiModelProperty(value = "box color", required = true, example = "#FFEE00")
    private String colorBox;            // format: #ff0000
    @ApiModelProperty(value = "destination country name", required = true, example = "Sweden")
    private String countryNameDest;     // Sweden

}
