#include <QAbstractTableModel>
#include <QBrush>
#include <Qt>
#include "domain.h"
#include <vector>
#include <qdebug.h>
#include <QFont>
#include "map"

using std::vector;


class MyTableModel :public QAbstractTableModel {
    std::vector<Melodie> melodii;
    map<string,int> artisti;
    map<string, int> genuri;

public:
    MyTableModel(const vector<Melodie>& melodii, map<string,int> artisti, map<string, int> genuri) :melodii{ melodii }, artisti{ artisti }, genuri{ genuri } {
    }

    int rowCount(const QModelIndex& parent = QModelIndex()) const override {
        return melodii.size();
    }
    int columnCount(const QModelIndex& parent = QModelIndex()) const override {
        return 6;
    }

    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {

        if (role == Qt::DisplayRole) {

            Melodie c = melodii[index.row()];
            if (index.column() == 0) {
                return QString::number(c.get_id());
            }
            else if (index.column() == 1) {
                return QString::fromStdString(c.get_titlu());
            }
            else if (index.column() == 2) {
                return QString::fromStdString(c.get_artist());
            }
            else if (index.column() == 3) {
                return QString::fromStdString(c.get_gen());
            }
            else if (index.column() == 4) {
                return QString::number(genuri.at(c.get_gen()));
            }
            else if (index.column() == 5) {
                return QString::number(artisti.at(c.get_artist()));
            }
        }

        return QVariant{};
    }

    void set_melodii(const vector<Melodie> melodi, map<string,int> fr_artisti, map<string, int> fr_genuri) {
        this->melodii = melodi;
        this->artisti = fr_artisti;
        this->genuri = fr_genuri;
        auto topLeft = createIndex(0, 0);
        auto bottomR = createIndex(rowCount(), columnCount());
        emit layoutChanged();
        emit dataChanged(topLeft, bottomR);
    }

};
