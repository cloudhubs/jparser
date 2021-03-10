package edu.baylor.ecs.cloudhubs.jparser.ast.expr;

public enum Op {
    Plus,
    Minus,
    Star,
    Slash,
    Modulus,
    PlusPlus,
    MinusMinus,

    And,
    Pipe,
    Carat,
    Tilde,
    BitShiftLeft,
    BitShiftRight,
    UnsignedBitShiftRight,

    FatArrow,
    ThinArrow,
    ExclamationPoint,
    QuestionMark,
    Colon,

    Equal,
    PlusEqual,
    MinusEqual,
    StarEqual,
    SlashEqual,
    ModulusEqual,
    AndEqual,
    PipeEqual,
    CaratEqual,
    TildeEqual,
    ShiftLeftEqual,
    ShiftRightEqual,
    UnsignedShiftRightEqual,

    AndAnd,
    PipePipe,
    EqualEqual,
    NotEqual,
    GreaterThan,
    GreaterThanEqualTo,
    LessThan,
    LessThanEqualTo,

    Spaceship,

    // Need to properly handle this..
    Other,
}
