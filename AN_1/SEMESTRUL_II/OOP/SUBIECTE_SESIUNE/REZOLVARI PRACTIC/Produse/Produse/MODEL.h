#pragma once
#include <QAbstractTableModel>
#include <QBrush>
#include <Qt>
#include "domain.h"
#include <vector>
#include <qdebug.h>
#include <QFont>

using std::vector;


class MyTableModel :public QAbstractTableModel {
private:
    vector<Produs> produse;
    int nr;

public:
    MyTableModel(const vector<Produs>& produse, int nr) :produse{ produse }, nr{nr}{
    }
    int rowCount(const QModelIndex& parent = QModelIndex()) const override {
        return produse.size();
    }
    int columnCount(const QModelIndex& parent = QModelIndex()) const override {
        return 5;
    }



    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
      
        if (role == Qt::BackgroundRole) {
            auto c = produse[index.row()];
            int pret = c.get_pret();
            int row = index.row();
            if (pret <= nr) {
                QBrush bg(Qt::darkRed);
                return bg;
            }
        }

        if (role == Qt::DisplayRole)
        {
            Produs c = produse[index.row()];
            if (index.column() == 0) {
                return QString::number(c.get_id());
            }
            else if (index.column() == 1) {
                return QString::fromStdString(c.get_nume());
            }
            else if (index.column() == 2) {
                return QString::fromStdString(c.get_tip());
            }
            else if (index.column() == 3) {
                return QString::number(c.get_pret());
            }
            else if (index.column() == 4) {
                return QString::number(c.nr_vocale());
            }
        }

        return QVariant{};
    }

    void set_produse(vector<Produs> produse, int nr) {
        this->produse = produse;
        this->nr = nr;
        auto topLeft = createIndex(0, 0);
        auto bottomR = createIndex(rowCount(), columnCount());
        emit layoutChanged();
        emit dataChanged(topLeft, bottomR);
    }

    QVariant headerData(int section, Qt::Orientation orientation, int role) const
    {
        if (role == Qt::DisplayRole)
        {
            if (orientation == Qt::Horizontal) {
                switch (section)
                {
                case 0:
                    return tr("ID");
                case 1:
                    return tr("NUME");
                case 2:
                    return tr("TIP");
                case 3:
                    return tr("PRET");
                case 4:
                    return tr("VOCALE");
                default:
                    return QString("");
                }
            }
        }
        return QVariant();
    }
};