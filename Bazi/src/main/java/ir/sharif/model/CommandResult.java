package ir.sharif.model;

import ir.sharif.enums.*;

public record CommandResult(ResultCode statusCode, String message) { }
