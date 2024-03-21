#include "programREAD.h"
programReadOnlyGUI::programReadOnlyGUI(Program& program) : program{ program } {
    this->program.addObserver(this);
    this->repaint();
}

void programReadOnlyGUI::paintEvent(QPaintEvent* event) {
    QPainter g{ this };
    g.setPen(Qt::blue);


    std::mt19937 mt{ std::random_device{}() };
    std::uniform_int_distribution<> x_coord(0, this->width());
    std::uniform_int_distribution<> y_coord(0, this->height());
    std::uniform_int_distribution<> width(15, RECTANGLE_MAX_DIM);
    std::uniform_int_distribution<> height(15, RECTANGLE_MAX_DIM);

    for (const auto& prod : this->program.get_all()) {
        g.drawEllipse(x_coord(mt), y_coord(mt), width(mt), height(mt));

    }
}

void programReadOnlyGUI::update() {
    this->repaint();
}