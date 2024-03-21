#include "Observer.h"
#include "service.h"
#include "program.h"
#include <qwidget.h>
#include <qpainter.h>
#include <random>
#include <cmath>

#define RECTANGLE_MAX_DIM 256

class programReadOnlyGUI : public QWidget, public Observer {
private:
    Program& program;

public:
    programReadOnlyGUI(Program& program);

    void update() override;

    void paintEvent(QPaintEvent* event) override;

    ~programReadOnlyGUI() {
        this->program.removeObserver(this);
    }
};
